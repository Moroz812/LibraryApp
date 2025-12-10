package com.example.libraryapp.dto;

public class AuthorResponse {
    private Long id;
    private String name;
    private Integer bookCount; //счётчик сколько книг у автора

    public AuthorResponse() {
    }

    public AuthorResponse(String name, Integer bookCount) {
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
