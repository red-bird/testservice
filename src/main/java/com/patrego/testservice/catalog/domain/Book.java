package com.patrego.testservice.catalog.domain;

import com.patrego.testservice.common.domain.valueObjects.Id;
import com.patrego.testservice.catalog.domain.valueObjects.Isbn;

import java.util.Objects;

public class Book {
    private Id id;
    private String title;
    private Isbn isbn;

    public Book(Id id, String title, Isbn isbn) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(isbn, "isbn must not be null");
        this.id = id == null ? new Id() : id;
        this.title = title;
        this.isbn = isbn;
    }

    public Id getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Isbn getIsbn() {
        return isbn;
    }

}
