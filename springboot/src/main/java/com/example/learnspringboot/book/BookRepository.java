package com.example.learnspringboot.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String> {
    Optional<BookEntity> findBookEntityById(String Id);

    @Query(value = "select b from books b where (:name is null or b.name = :name) and (:reading is null or b.reading = :reading) and (:finished is null or b.finished= :finished)")
    List<BookEntity> findBookEntitiesByNameAndReadingAndFinished(String name, boolean reading, boolean finished);
}
