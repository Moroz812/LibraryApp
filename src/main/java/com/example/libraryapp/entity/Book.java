package com.example.libraryapp.entity;

import jakarta.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    //проверять на null
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    //проверять на null
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    private Integer publicationYear;
    private String isbn;
    private String description;
    private Double averageRating;
    private Integer pageCount;

    public Book() {
    }

    public Book(String title, Author author, Genre genre, Integer publicationYear, String isbn, String description, Double averageRating, Integer pageCount) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.description = description;
        this.averageRating = averageRating;
        this.pageCount = pageCount;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
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

    public Double getAverageRating() {
        return averageRating;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
