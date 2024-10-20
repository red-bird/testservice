package com.patrego.testservice.catalog.domain;

import com.patrego.testservice.common.domain.valueObjects.Id;

import java.util.List;

public interface BookRepository {
    Book saveBook(Book book);
    List<Book> getAllBooks();
    Book getBookById(Id id);
    Book updateBook(Book book, Id id);
    void deleteBookById(Id id);

}
