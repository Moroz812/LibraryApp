package com.example.libraryapp.dto;

public class GenreResponse {
    private Long id;
    private String name;
    private Integer bookCount; //счётчик сколько книг в жанре

    public GenreResponse() {
    }

    public GenreResponse(String name, String description, Integer bookCount) {
        this.name = name;
        this.bookCount = bookCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }
}
