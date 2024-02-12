package com.example.learnspringboot.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, String> {
    List<BookEntity> findBookEntitiesByNameContainingIgnoreCase(String name);

    Optional<BookEntity> findBookEntityById(String Id);

    List<BookEntity> findBookEntitiesByPublisherContainingIgnoreCase(String publisher);
}
