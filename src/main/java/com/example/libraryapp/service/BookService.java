package com.example.libraryapp.service;

import com.example.libraryapp.dto.BookRequest;
import com.example.libraryapp.dto.BookResponse;
import com.example.libraryapp.entity.Author;
import com.example.libraryapp.entity.Book;
import com.example.libraryapp.entity.Genre;
import com.example.libraryapp.repository.AuthorRepository;
import com.example.libraryapp.repository.BookRepository;
import com.example.libraryapp.repository.GenreRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }


    public BookResponse createBook(BookRequest request) {
        //сохраняем без проверок, добавлю в другой день
        //нужны проверки уникальности названия, что жанр существует, автор существует
        Book book = new Book();
        book.setTitle(request.getTitle());
        //book.setAuthor(new Author()); //заглушка
        //book.setGenre(new Genre()); //заглушка
        book.setPublicationYear(request.getPublicationYear());
        book.setIsbn(request.getIsbn());
        book.setDescription(request.getDescription());
        book.setPageCount(request.getPageCount());
        book.setAverageRating(0.0);

        Book savedBook = bookRepository.save(book);
        return mapToResponse(savedBook);
    }


    public @NotNull BookResponse getBookById(Long id) {
        //заглушка - вернем тестовую книгу
        Book book = new Book();
        book.setId(id);
        book.setTitle("Тестовая книга");
        book.setAuthor(new Author());
        book.setGenre(new Genre());
        return mapToResponse(book);
    }


    public @NotNull List<BookResponse> getAllBooks() {
        //вернем пустой список или тестовые данные
        List<BookResponse> responses = new ArrayList<>();

        BookResponse book1 = new BookResponse();
        book1.setId(1L);
        book1.setTitle("Война и мир");
        book1.setAuthorName("Лев Толстой");
        book1.setGenreName("Роман");
        responses.add(book1);

        BookResponse book2 = new BookResponse();
        book2.setId(2L);
        book2.setTitle("Преступление и наказание");
        book2.setAuthorName("Фёдор Достоевский");
        book2.setGenreName("Роман");
        responses.add(book2);

        return responses;
    }


    public @NotNull BookResponse updateBook(Long id, @Valid BookRequest request) {
        //заглушка - вернем обновленную книгу
        //нужны проверки уникальности названия, если название изменилось, проверка жанра, проверка автора и поиск книги
        BookResponse response = new BookResponse();
        response.setId(id);
        response.setTitle(request.getTitle() + " (обновлено)");
        response.setAuthorName("Автор");
        response.setGenreName("Жанр");
        return response;
    }


    public void deleteBook(Long id) {
        //просто выводим в консоль
        //добавить проверку, что книга существует
        System.out.println("Удаление книги с ID: " + id);
    }

    //метод преобразует любой объект Book в BookResponse
    //не забыть добавить проверку на null
    private BookResponse mapToResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthorName("Автор"); //заглушка
        response.setGenreName("Жанр"); //заглушка
        response.setPublicationYear(book.getPublicationYear());
        response.setIsbn(book.getIsbn());
        response.setDescription(book.getDescription());
        response.setAverageRating(book.getAverageRating());
        response.setPageCount(book.getPageCount());
        return response;
    }
}
