package com.example.learnspringboot.book.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateBookDto(
    @NotBlank(message = "Gagal menambahkan buku. Mohon isi nama buku")
    String name,
    int year,
    @NotBlank
    String author,
    @NotBlank
    String summary,
    @NotBlank
    String publisher,
    @Min(0)
    int pageCount,
    @Min(0)
    int readPage,
    @NotNull
    boolean reading

) {

}
