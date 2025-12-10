package com.example.libraryapp.dto;

import jakarta.validation.constraints.NotBlank;

public class GenreRequest {
    @NotBlank(message = "Название жанра обязательно")
    private String name;

    public GenreRequest() {
    }

    public GenreRequest(String name, String description) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
