package com.example.libraryapp.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String biography;
    private LocalDate birthDate;
    private LocalDate deathDate; //может быть null

    public Author() {
    }

    public Author(String name, String biography, LocalDate birthDate, LocalDate deathDate) {
        this.name = name;
        this.biography = biography;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    public String getName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
