package com.example.learnspringboot.book.mapper;

import com.example.learnspringboot.book.BookEntity;
import com.example.learnspringboot.book.dto.CreateBookDto;

public class BookMapper {
    public static BookEntity toEntity(CreateBookDto bookDto) {
        return new BookEntity.Builder()
                .setName(bookDto.name())
                .setYear(bookDto.year())
                .setAuthor(bookDto.author())
                .setSummary(bookDto.summary())
                .setPublisher(bookDto.publisher())
                .setPageCount(bookDto.pageCount())
                .setReadPage(bookDto.readPage())
                .setReading(bookDto.reading())
                .createBookEntity();
    }
}
