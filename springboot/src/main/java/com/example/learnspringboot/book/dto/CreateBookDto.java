package com.example.learnspringboot.book.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record CreateBookDto(
        @NotBlank
        @NotEmpty
        @NotNull
        String name,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime year,
        @NotBlank
        @NotEmpty
        @NotNull
        String author,
        @NotBlank
        @NotEmpty
        @NotNull
        String summary,
        @NotBlank
        @NotEmpty
        @NotNull
        String publisher,
        @Min(1)
        @NotNull
        int pageCount,
        @NotNull
        @Min(1)
        int readPage,
        @NotNull
        boolean reading

) {

}
