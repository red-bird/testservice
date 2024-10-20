package com.patrego.testservice.catalog.application.services;

import com.patrego.testservice.catalog.domain.Book;
import com.patrego.testservice.common.domain.valueObjects.Id;

import java.util.List;

public interface BookService {
    Book saveBook(Book book);
    List<Book> getAllBooks();
    Book getBookById(Id id);
    Book updateBookById(Book book, Id id);
    void deleteBookById(Id id);
}
