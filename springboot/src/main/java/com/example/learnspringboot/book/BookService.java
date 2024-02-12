package com.example.learnspringboot.book;

import com.example.learnspringboot.book.dto.CreateBookDto;
import com.example.learnspringboot.book.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public void saveBook(CreateBookDto bookDto) {
        BookEntity newBook = BookMapper.toEntity(bookDto);
        repository.save(newBook);
    }
}
