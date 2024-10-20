package com.patrego.testservice.catalog;

import com.patrego.testservice.catalog.application.rest.dto.BookDto;
import com.patrego.testservice.catalog.infrastructure.db.BookEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class BookControllerIT {

    private final MockMvc mockMvc;
    private final BookEntityRepository repository;

    @Autowired
    public BookControllerIT(MockMvc mockMvc, BookEntityRepository repository) {
        this.mockMvc = mockMvc;
        this.repository = repository;
    }

    @Rollback
    @Transactional
    public void generateBooks(Long quantity, List<UUID> bookIds) throws Exception {

        for (int i = 0; i < quantity; i++) {
            String bookName = "Book " + i;
            String isbn = IsbnGenerator.generateIsbn13();
            UUID uuid = UUID.randomUUID();

            if (Objects.nonNull(bookIds)) bookIds.add(uuid);

            BookDto bookDto = new BookDto(uuid.toString(), bookName, isbn);
            mockMvc.perform(post("/api/v1/books")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(ObjToJsonConverter.asJsonString(bookDto)))
                    .andExpect(status().isCreated());
        }
    }

    @Test
    public void shouldCreate100000Books() throws Exception {
        generateBooks(100_000L, null);
        repository.deleteAll();
    }


    private static final int NUM_THREADS = 100;
    private static final int NUM_REQUESTS = 1_000_000;
    private final List<UUID> bookIds = new ArrayList<>();

    @Test
    public void testGetBooksPerformance() throws Exception {
        generateBooks(10_000L, bookIds);

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<Long>> results = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        // Запуск 1 миллиона запросов через 100 потоков
        for (int i = 0; i < NUM_REQUESTS; i++) {
            Future<Long> result = executorService.submit(new QueryTask(mockMvc, bookIds));
            results.add(result);
        }

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.MINUTES);

        long endTime = System.currentTimeMillis();
        System.out.println("Total time: " + (endTime - startTime) + " ms");

        // Сбор времени запросов
        List<Long> queryTimes = new ArrayList<>();
        for (Future<Long> result : results) {
            queryTimes.add(result.get());
        }

        // Подсчет статистики
        collectStatistics(queryTimes);

        // Очистка
        repository.deleteAll();
    }

    // Класс для выполнения запроса
    static class QueryTask implements Callable<Long> {
        private final MockMvc mockMvc;
        private final List<UUID> bookIds;

        public QueryTask(MockMvc mockMvc, List<UUID> bookIds) {
            this.mockMvc = mockMvc;
            this.bookIds = bookIds;
        }

        @Override
        public Long call() throws Exception {
            UUID randomId = bookIds.get(ThreadLocalRandom.current().nextInt(bookIds.size())); // Случайный UUID из списка
            long startTime = System.nanoTime();
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/" + randomId))
                    .andExpect(status().isOk());
            long endTime = System.nanoTime();
            return TimeUnit.NANOSECONDS.toMicros(endTime - startTime);
        }
    }

    // Сбор статистики
    public void collectStatistics(List<Long> queryTimes) {
        queryTimes.sort(Long::compareTo);

        long totalRequests = queryTimes.size();
        long totalTime = queryTimes.stream().mapToLong(Long::longValue).sum();
        double averageTime = totalTime / (double) totalRequests;

        long medianTime = -1;
        if (totalRequests % 2 == 0) {
            medianTime = (queryTimes.get((int) totalRequests / 2 - 1) + queryTimes.get((int) totalRequests / 2)) / 2;
        } else {
            medianTime = queryTimes.get((int) totalRequests / 2);
        }
        long percentile95 = queryTimes.get((int) (totalRequests * 0.95));
        long percentile99 = queryTimes.get((int) (totalRequests * 0.99));

        System.out.println("Total Requests: " + totalRequests);
        System.out.println("Average Time: " + averageTime + " μs or " + averageTime/1000 + " ms");
        System.out.println("Median Time: " + medianTime + " μs or " + medianTime/1000 + " ms");
        System.out.println("95th Percentile: " + percentile95 + " μs or " + percentile95/1000 + " ms");
        System.out.println("99th Percentile: " + percentile99 + " μs or " + percentile99/1000 + " ms");
    }

}
