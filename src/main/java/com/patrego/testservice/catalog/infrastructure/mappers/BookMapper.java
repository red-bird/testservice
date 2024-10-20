package com.patrego.testservice.catalog.infrastructure.mappers;

import com.patrego.testservice.catalog.application.rest.dto.BookDto;
import com.patrego.testservice.catalog.domain.Book;
import com.patrego.testservice.catalog.domain.valueObjects.Isbn;
import com.patrego.testservice.catalog.infrastructure.db.BookEntity;
import com.patrego.testservice.common.infrastructure.mappers.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper extends BaseMapper<BookDto, Book, BookEntity> {

    default Isbn stringToIsbn(String isbn) {
        return isbn != null ? new Isbn(isbn) : null;
    }

    default String isbnToString(Isbn isbn) {
        return isbn != null ? isbn.value() : null;
    }

}
