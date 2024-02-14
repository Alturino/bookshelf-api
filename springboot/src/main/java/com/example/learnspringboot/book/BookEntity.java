package com.example.learnspringboot.book;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "books", indexes = {@Index(columnList = "name"), @Index(columnList = "author"), @Index(columnList = "publisher")})
public class BookEntity {
    @Id
    @Column
    private String Id;

    @NotNull(message = "Gagal menambahkan buku. Mohon isi nama buku")
    @NotEmpty(message = "Gagal menambahkan buku. Mohon isi nama buku")
    @NotBlank(message = "Gagal menambahkan buku. Mohon isi nama buku")
    @Column
    private String name;

    @NotNull
    @Column
    private int year;

    @NotNull
    @NotEmpty
    @NotBlank
    @Column
    private String author;
    @NotNull
    @NotEmpty
    @NotBlank
    @Column
    private String summary;
    @NotNull
    @NotEmpty
    @NotBlank
    @Column
    private String publisher;
    @NotNull
    @Min(1)
    @Column(name = "page_count")
    private int pageCount;
    @NotNull
    @Min(1)
    @Column(name = "read_page")
    private int readPage;
    @NotNull
    @Column(name = "is_reading")
    private boolean reading;

    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "inserted_at", updatable = false)
    private LocalDateTime insertedAt;

    @UpdateTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @NotNull
    @Column(name = "is_finished")
    private boolean finished;

    public BookEntity() {
    }

    public BookEntity(String id, String name, int year, String author, String summary, String publisher, int pageCount, int readPage, boolean reading, boolean finished) {
        this.Id = id;
        this.name = name;
        this.year = year;
        this.author = author;
        this.summary = summary;
        this.publisher = publisher;
        this.pageCount = pageCount;
        this.readPage = readPage;
        this.reading = reading;
        this.insertedAt = insertedAt;
        this.updatedAt = updatedAt;
        this.finished = finished;
    }

    public BookEntity(String name, int year, String author, String summary, String publisher, int pageCount, int readPage, boolean reading, boolean finished) {
        this.name = name;
        this.year = year;
        this.author = author;
        this.summary = summary;
        this.publisher = publisher;
        this.pageCount = pageCount;
        this.readPage = readPage;
        this.reading = reading;
        this.finished = finished;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getReadPage() {
        return readPage;
    }

    public void setReadPage(int readPage) {
        this.readPage = readPage;
    }

    public boolean isReading() {
        return reading;
    }

    public void setReading(boolean reading) {
        this.reading = reading;
    }

    public LocalDateTime getInsertedAt() {
        return insertedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public static class Builder {
        private String name;
        private int year;
        private String author;
        private String summary;
        private String publisher;
        private int pageCount;
        private int readPage;
        private boolean reading;
        private boolean finished;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setYear(int year) {
            this.year = year;
            return this;
        }

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder setPublisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder setPageCount(int pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public Builder setReadPage(int readPage) {
            this.readPage = readPage;
            return this;
        }

        public Builder setReading(boolean reading) {
            this.reading = reading;
            return this;
        }

        public Builder setFinished(boolean finished) {
            this.finished = finished;
            return this;
        }

        public BookEntity createBookEntity() {
            return new BookEntity(UUID.randomUUID().toString(), name, year, author, summary, publisher, pageCount, readPage, reading, finished);
        }
    }
}
