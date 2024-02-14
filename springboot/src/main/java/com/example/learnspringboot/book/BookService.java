package com.example.learnspringboot.book;

import com.example.learnspringboot.book.dto.CreateBookDto;
import com.example.learnspringboot.book.dto.UpdateBookDto;
import com.example.learnspringboot.book.mapper.BookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@CacheConfig(cacheNames = "books")
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public BookEntity saveBook(CreateBookDto createBookDto) {
        BookEntity newBook = BookMapper.toEntity(createBookDto);
        return repository.save(newBook);
    }

    @CachePut(cacheNames = "books", key = "#id")
    public BookEntity updateBook(UpdateBookDto bookDto, String id) {
        BookEntity oldBook = repository.findBookEntityById(id)
            .orElseThrow(() -> new NoSuchElementException("Gagal memperbarui buku. Id tidak ditemukan"));
        BookEntity newBook = BookMapper.updateBook(bookDto, oldBook);
        return repository.save(newBook);
    }

    @CacheEvict(cacheNames = "books", key = "#id")
    public void deleteBook(String id) {
        boolean isExist = repository.existsById(id);
        if (!isExist) {
            throw new NoSuchElementException("Buku gagal dihapus. Id tidak ditemukan");
        }
        repository.deleteById(id);
    }

    @Caching(cacheable = {@Cacheable(cacheNames = "books", unless = "#name == null or #reading == null or #finished == null")}, evict = {@CacheEvict(cacheNames = "books")})
    public List<BookEntity> getBook(String name, Integer reading, Integer finished) {
        return repository.findBookEntitiesByNameAndReadingAndFinished(name, reading != null && reading == 1, finished != null && finished == 1);
    }

    @Cacheable(cacheNames = "books", key = "#id")
    public BookEntity getBookById(String id) {
        return repository.findBookEntityById(id).orElseThrow(() -> new NoSuchElementException("Buku tidak ditemukan"));
    }
}
