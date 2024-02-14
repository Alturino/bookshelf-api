package com.example.learnspringboot.book;

import com.example.learnspringboot.book.dto.CreateBookDto;
import com.example.learnspringboot.book.dto.GetBookDto;
import com.example.learnspringboot.book.dto.UpdateBookDto;
import com.example.learnspringboot.book.mapper.BookMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "books", produces = {MediaType.APPLICATION_JSON_VALUE})
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllBook(@RequestParam(required = false) String name,
                                                          @RequestParam(required = false) @Valid @Min(0) @Max(1) Integer reading,
                                                          @RequestParam(required = false) @Valid @Min(0) @Max(1) Integer finished) {
        List<GetBookDto> books = bookService.getBook(name, reading, finished).stream().map(BookMapper::toGetBookDto).toList();
        Map<String, Object> data = Map.ofEntries(Map.entry("books", books));
        Map<String, Object> responseBody = new HashMap<>(Map.ofEntries(Map.entry("status", "success")));
        responseBody.put("data", data);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Map<String, Object>> getBookById(@PathVariable(value = "id") String id) {
        BookEntity book = bookService.getBookById(id);
        Map<String, Object> data = Map.of("book", book);
        Map<String, Object> responseBody = new HashMap<>(Map.ofEntries(Map.entry("status", "success")));
        responseBody.put("data", data);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveBook(@Valid @RequestBody CreateBookDto createBookDto) {
        if (createBookDto.readPage() > createBookDto.pageCount()) {
            Map<String, Object> errorBody = Map.ofEntries(Map.entry("status", "fail"), Map.entry("message", "Gagal menambahkan buku. readPage tidak boleh lebih besar dari pageCount"));
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorBody);
        }
        BookEntity savedBook = bookService.saveBook(createBookDto);
        Map<String, Object> data = Map.ofEntries(Map.entry("bookId", savedBook.getId()));
        Map<String, Object> responseBody = new HashMap<>(Map.ofEntries(Map.entry("status", "success"), Map.entry("message", "Buku berhasil ditambahkan")));
        responseBody.put("data", data);
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(responseBody);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateBook(@Valid @RequestBody UpdateBookDto bookDto, @PathVariable("id") String id) {
        if (bookDto.readPage() > bookDto.pageCount()) {
            Map<String, Object> errorBody = Map.ofEntries(Map.entry("status", "fail"), Map.entry("message", "Gagal memperbarui buku. readPage tidak boleh lebih besar dari pageCount"));
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorBody);
        }

        bookService.updateBook(bookDto, id);
        Map<String, Object> responseBody = Map.ofEntries(Map.entry("status", "success"), Map.entry("message", "Buku berhasil diperbarui"));
        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteBook(@PathVariable("id") String id) {
        bookService.deleteBook(id);
        Map<String, Object> responseBody = Map.ofEntries(Map.entry("status", "success"), Map.entry("message", "Buku berhasil dihapus"));
        return ResponseEntity.ok(responseBody);
    }
}
