package com.patrego.testservice.catalog.infrastructure.db;

import com.patrego.testservice.catalog.domain.Book;
import com.patrego.testservice.catalog.domain.BookRepository;
import com.patrego.testservice.common.domain.valueObjects.Id;
import com.patrego.testservice.catalog.infrastructure.mappers.BookListMapper;
import com.patrego.testservice.catalog.infrastructure.mappers.BookMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JpaBookRepository implements BookRepository {
    private final BookEntityRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookListMapper bookListMapper;

    @Override
    @Transactional
    public Book saveBook(Book book) {
        BookEntity entity = bookMapper.domainToEntity(book);
        entity = bookRepository.save(entity);
        return bookMapper.entityToDomain(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookListMapper.entityToDomain(bookRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(Id id) {
        Optional<BookEntity> byId = bookRepository.findById(id.id());
        return byId.isPresent() ? bookMapper.entityToDomain(byId.get()) : null;
    }

    @Override
    @Transactional
    public Book updateBook(Book book, Id id) {
        Optional<BookEntity> opt = bookRepository.findById(id.id());
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("Книга с индексом \"" + id.id() + "\" отсутствует");
        }
        BookEntity entity = opt.get();
        entity = bookMapper.domainToEntity(book);
        entity.setId(id.id());
        bookRepository.save(entity);
        return book;
    }

    @Override
    @Transactional
    public void deleteBookById(Id id) {
        if (!bookRepository.existsById(id.id())) {
            throw new EntityNotFoundException("Книга с индексом \"" + id.id() + "\" отсутствует");
        }
        bookRepository.deleteById(id.id());
    }
}
