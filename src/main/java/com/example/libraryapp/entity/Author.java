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
}
