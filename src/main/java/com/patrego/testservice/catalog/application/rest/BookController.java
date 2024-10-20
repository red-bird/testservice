package com.patrego.testservice.catalog.application.rest;

import com.patrego.testservice.catalog.application.rest.dto.BookDto;
import com.patrego.testservice.catalog.application.services.BookService;
import com.patrego.testservice.catalog.domain.Book;
import com.patrego.testservice.catalog.infrastructure.mappers.BookListMapper;
import com.patrego.testservice.catalog.infrastructure.mappers.BookMapper;
import com.patrego.testservice.common.domain.valueObjects.Id;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/books")
public class BookController {
    private final BookService service;
    private final BookMapper bm;
    private final BookListMapper bmList;

    @PostMapping()
    public ResponseEntity<BookDto> saveBook(@RequestBody BookDto dto) {
        Book domain = service.saveBook(bm.dtoToDomain(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(bm.domainToDto(domain));
    }

    @Operation(
            summary = "Получить список всех книг",
            description = "Возвращает список книг."
    )
    @GetMapping()
    public List<BookDto> getAllBooks() {
        return bmList.domainToDto(service.getAllBooks());
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable String id) {
        Id uuid = new Id(UUID.fromString(id));
        Book book = service.getBookById(uuid);
        return ResponseEntity.ok().body(bm.domainToDto(book));
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDto> updateBookById(@PathVariable String id, @RequestBody BookDto dto) {
        Id uuid = new Id(UUID.fromString(id));
        Book book1 = bm.dtoToDomain(dto);
        Book book = service.updateBookById(book1, uuid);
        return ResponseEntity.ok().body(bm.domainToDto(book));
    }

    @DeleteMapping("{id}")
    public void deleteBookById(@PathVariable String id) {
        service.deleteBookById(new Id(UUID.fromString(id)));
    }

}
