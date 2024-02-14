package com.example.learnspringboot.book.mapper;

import com.example.learnspringboot.book.BookEntity;
import com.example.learnspringboot.book.dto.CreateBookDto;
import com.example.learnspringboot.book.dto.GetBookDto;
import com.example.learnspringboot.book.dto.UpdateBookDto;

public class BookMapper {
    public static BookEntity toEntity(CreateBookDto createBookDto) {
        return new BookEntity.Builder()
            .setAuthor(createBookDto.author())
            .setFinished(createBookDto.pageCount() == createBookDto.readPage())
            .setName(createBookDto.name())
            .setPageCount(createBookDto.pageCount())
            .setPublisher(createBookDto.publisher())
            .setReadPage(createBookDto.readPage())
            .setReading(createBookDto.reading())
            .setSummary(createBookDto.summary())
            .setYear(createBookDto.year())
            .createBookEntity();
    }

    public static GetBookDto toGetBookDto(BookEntity bookEntity) {
        return new GetBookDto(bookEntity.getId(), bookEntity.getName(), bookEntity.getPublisher());
    }

    public static BookEntity updateBook(UpdateBookDto bookDto, BookEntity oldBook) {
        oldBook.setAuthor(bookDto.author());
        oldBook.setFinished(bookDto.pageCount() == bookDto.readPage());
        oldBook.setName(bookDto.name());
        oldBook.setPageCount(bookDto.pageCount());
        oldBook.setPublisher(bookDto.publisher());
        oldBook.setReadPage(bookDto.readPage());
        oldBook.setSummary(bookDto.summary());
        oldBook.setYear(bookDto.year());
        return oldBook;
    }
}
