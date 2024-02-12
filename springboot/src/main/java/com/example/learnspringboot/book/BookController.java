package com.example.learnspringboot.book;

import com.example.learnspringboot.book.dto.CreateBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "books", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH})
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookEntity>> getBook() {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @PostMapping
    public ResponseEntity<CreateBookDto> saveBook(@RequestBody CreateBookDto bookDto) {
        if (bookDto.readPage() > bookDto.pageCount()) {
            throw new IllegalArgumentException("Book readPage should not be more than pageCount");
        }
        bookService.saveBook(bookDto);
        return ResponseEntity.ok(bookDto);
    }

}
