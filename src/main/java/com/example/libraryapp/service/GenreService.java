package com.example.libraryapp.service;

import com.example.libraryapp.dto.GenreRequest;
import com.example.libraryapp.dto.GenreResponse;
import com.example.libraryapp.entity.Genre;
import com.example.libraryapp.exception.DuplicateGenreException;
import com.example.libraryapp.exception.GenreHasBooksException;
import com.example.libraryapp.exception.GenreNotFoundException;
import com.example.libraryapp.repository.BookRepository;
import com.example.libraryapp.repository.GenreRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GenreService {
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    public GenreService(BookRepository bookRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
    }


    public GenreResponse createGenre(@Valid GenreRequest request) {
        //проверка уникальности
        if (genreRepository.findByName(request.getName()).isPresent()) {
            throw new DuplicateGenreException("Жанр с названием '" + request.getName() + "' уже существует");
        }

        Genre genre = new Genre();
        genre.setName(request.getName());

        Genre savedGenre = genreRepository.save(genre);
        return mapToResponse(savedGenre);
    }


    public List<GenreResponse> getAllGenre() {
        List<Genre> genres = genreRepository.findAll();
        List<GenreResponse> responses = new ArrayList<>();

        for (Genre genre : genres) {
            responses.add(mapToResponse(genre));
        }

        return responses;
    }

    public GenreResponse getGenreById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException("Жанр с ID " + id + " не найден"));

        return mapToResponse(genre);
    }


    public GenreResponse updateGenre(Long id, @Valid GenreRequest request) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException("Жанр с ID " + id + " не найден"));
        //проверка уникальности
        if (!genre.getName().equals(request.getName())) {
            if (genreRepository.findByName(request.getName()).isPresent()) {
                throw new DuplicateGenreException("Жанр с названием '" + request.getName() + "' уже существует");
            }
        }
        genre.setName(request.getName());

        Genre updatedGenre = genreRepository.save(genre);
        return mapToResponse(updatedGenre);
    }

    public void deleteGenre(Long id) {
        if (!genreRepository.existsById(id)) {
            throw new GenreNotFoundException("Жанр с ID " + id + " не найден");
        }
        //проверка, есть ли книги в этом жанре
        List<com.example.libraryapp.entity.Book> books = bookRepository.findByGenreId(id);
        if (!books.isEmpty()) {
            throw new GenreHasBooksException(
                    "Нельзя удалить жанр, в котором есть книги. Количество книг: " + books.size()
            );
        }

        genreRepository.deleteById(id);
    }

    private GenreResponse mapToResponse(Genre genre) {
        GenreResponse response = new GenreResponse();
        response.setId(genre.getId());
        response.setName(genre.getName());
        //подсчитать книги в жанре
        List<com.example.libraryapp.entity.Book> books = bookRepository.findByGenreId(genre.getId());
        response.setBookCount(books.size());

        return response;
    }
}
