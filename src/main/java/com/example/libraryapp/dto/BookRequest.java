package com.example.libraryapp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookRequest {
    @NotBlank(message = "Название книги обязательно")
    private String title;

    @NotNull(message = "ID автора обязательно")
    private Long authorId;

    @NotNull(message = "ID жанра обязательно")
    private Long genreId;

    //позже переделать валидацию, чтобы каждый новый год не менять максимум
    @NotNull(message = "Год публикации обязателен")
    @Min(value = 1800, message = "Год должен быть >= 1800")
    @Max(value = 2025, message = "Год должен быть <= 2025")
    private Integer publicationYear;

    //международный стандартный книжный номер, потом добавить валидацию
    private String isbn;
    private String description;
    private Integer pageCount;

    public BookRequest() {
    }

    public BookRequest(String title, Integer publicationYear, String isbn, String description, Integer pageCount) {
        this.title = title;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.description = description;
        this.pageCount = pageCount;
    }

    public String getTitle() {
        return title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPageCount() {
        return pageCount;
    }
}
