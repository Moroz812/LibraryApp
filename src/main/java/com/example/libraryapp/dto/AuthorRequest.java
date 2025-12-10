package com.example.libraryapp.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthorRequest {
    @NotBlank(message = "Имя автора обязательно")
    private String name;

    public AuthorRequest() {
    }

    public AuthorRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}