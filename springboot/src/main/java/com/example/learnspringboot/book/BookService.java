package com.example.learnspringboot.book;

import com.example.learnspringboot.book.dto.CreateBookDto;
import com.example.learnspringboot.book.dto.UpdateBookDto;
import com.example.learnspringboot.book.mapper.BookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        logger.info("Constructing BookService");
        logger.info("Initializing BookRepository");
        this.repository = repository;
        logger.info("BookRepository Initialized");
    }

    public BookEntity saveBook(CreateBookDto createBookDto) {
        logger.info("Calling function saveBook");
        logger.info("Creating BookEntity from createBookDto: " + createBookDto.toString());
        BookEntity newBook = BookMapper.toEntity(createBookDto);
        logger.info("BookEntity created BookEntity: " + newBook.toString());
        logger.info("Saving BookEntity");
        BookEntity savedBook = repository.save(newBook);
        logger.info("BookEntity Saved");
        return savedBook;
    }

    public BookEntity updateBook(UpdateBookDto bookDto, String id) {
        BookEntity oldBook = repository.findBookEntityById(id)
            .orElseThrow(() -> new NoSuchElementException("Gagal memperbarui buku. Id tidak ditemukan"));
        BookEntity newBook = BookMapper.updateBook(bookDto, oldBook);
        return repository.save(newBook);
    }

    public void deleteBook(String id) {
        boolean isExist = repository.existsById(id);
        if (!isExist) {
            throw new NoSuchElementException("Buku gagal dihapus. Id tidak ditemukan");
        }
        repository.deleteById(id);
    }

    public List<BookEntity> getBook(String name, Integer reading, Integer finished) {
        var books = repository.findAll();
        if (name != null) {
            books = books.stream().filter(book -> book.getName().contains(name)).toList();
        }
        if (reading != null) {
            books = books.stream().filter(book -> (reading == 1) == book.isReading()).toList();
        }
        if (finished != null) {
            books = books.stream().filter(book -> (finished == 1) == book.isFinished()).toList();
        }
        return books;
    }

    public BookEntity getBookById(String id) {
        return repository.findBookEntityById(id).orElseThrow(() -> new NoSuchElementException("Buku tidak ditemukan"));
    }
}
