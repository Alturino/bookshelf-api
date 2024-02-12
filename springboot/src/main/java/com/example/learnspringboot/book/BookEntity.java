package com.example.learnspringboot.book;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

@Entity
@Table(name = "book", schema = "book", indexes = {@Index(columnList = "name"), @Index(columnList = "author"), @Index(columnList = "publisher")})
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private String id;

    @NotNull
    @NotEmpty
    @NotBlank
    @Column
    private String name;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column
    private LocalDateTime year;

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

    @NotNull
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotNull
    @UpdateTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public BookEntity(String name, LocalDateTime year, String author, String summary, String publisher, int pageCount, int readPage, boolean reading, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.year = year;
        this.author = author;
        this.summary = summary;
        this.publisher = publisher;
        this.pageCount = pageCount;
        this.readPage = readPage;
        this.reading = reading;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public BookEntity() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getYear() {
        return year;
    }

    public void setYear(LocalDateTime year) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", author='" + author + '\'' +
                ", summary='" + summary + '\'' +
                ", publisher='" + publisher + '\'' +
                ", pageCount=" + pageCount +
                ", readPage=" + readPage +
                ", reading=" + reading +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public static class Builder {
        private String name;
        private LocalDateTime year;
        private String author;
        private String summary;
        private String publisher;
        private int pageCount;
        private int readPage;
        private boolean reading;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setYear(LocalDateTime year) {
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

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public BookEntity createBookEntity() {
            return new BookEntity(name, year, author, summary, publisher, pageCount, readPage, reading, createdAt, updatedAt);
        }
    }

}
