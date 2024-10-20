package com.patrego.testservice.catalog.application.services;

import com.patrego.testservice.common.domain.valueObjects.Id;
import com.patrego.testservice.catalog.domain.Book;
import com.patrego.testservice.catalog.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    public Book saveBook(Book book) {
        return repository.saveBook(book);
    }

    public List<Book> getAllBooks() {
        return repository.getAllBooks();
    }

    @Override
    public Book getBookById(Id id) {
        return repository.getBookById(id);
    }

    @Override
    public Book updateBookById(Book book, Id id) {
        return repository.updateBook(book, id);
    }

    @Override
    public void deleteBookById(Id id) {
        repository.deleteBookById(id);
    }

}
