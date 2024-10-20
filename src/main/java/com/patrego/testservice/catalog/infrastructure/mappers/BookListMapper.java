package com.patrego.testservice.catalog.infrastructure.mappers;

import com.patrego.testservice.catalog.application.rest.dto.BookDto;
import com.patrego.testservice.catalog.domain.Book;
import com.patrego.testservice.catalog.infrastructure.db.BookEntity;
import com.patrego.testservice.common.infrastructure.mappers.BaseListMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface BookListMapper extends BaseListMapper<BookDto, Book, BookEntity> {
}
