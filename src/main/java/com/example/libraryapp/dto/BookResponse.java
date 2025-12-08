package com.example.libraryapp.dto;

public class BookResponse {
    private Long id;
    private String title;
    private String authorName;
    private String genreName;
    private Integer publicationYear;
    private String isbn;
    private String description;
    private Double averageRating;
    private Integer pageCount;

    public BookResponse() {
    }

    public BookResponse(String title, String authorName, String genreName, Integer publicationYear, String isbn, String description, Double averageRating, Integer pageCount) {
        this.title = title;
        this.authorName = authorName;
        this.genreName = genreName;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.description = description;
        this.averageRating = averageRating;
        this.pageCount = pageCount;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getGenreName() {
        return genreName;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
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
}
