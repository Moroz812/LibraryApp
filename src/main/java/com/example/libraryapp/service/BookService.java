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
import java.util.stream.Collectors;

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
//        Book book = new Book();
//        book.setTitle(request.getTitle());
//        //book.setAuthor(new Author()); //заглушка
//        //book.setGenre(new Genre()); //заглушка
//        book.setPublicationYear(request.getPublicationYear());
//        book.setIsbn(request.getIsbn());
//        book.setDescription(request.getDescription());
//        book.setPageCount(request.getPageCount());
//        book.setAverageRating(0.0);
        System.out.println("=== CREATE BOOK ===");
        System.out.println("authorId из запроса: " + request.getAuthorId());
        System.out.println("genreId из запроса: " + request.getGenreId());

        //поиск существующего автора
        Author author = authorRepository.findById(request.getAuthorId()).orElse(null);
        System.out.println("Найден автор в базе: " + (author != null ? author.getName() + " (ID: " + author.getId() + ")" : "null"));

        if (author == null) {
            //создаём нового автора
            author = new Author();
            author.setName("Автор для книги");
            author = authorRepository.save(author);
            System.out.println("Создан новый автор с ID: " + author.getId());
        }

        //найти существующий жанр
        Genre genre = genreRepository.findById(request.getGenreId()).orElse(null);
        System.out.println("Найден жанр в базе: " + (genre != null ? genre.getName() + " (ID: " + genre.getId() + ")" : "null"));

        if (genre == null) {
            //создаём новый жанр
            genre = new Genre();
            genre.setName("Жанр для книги");
            genre = genreRepository.save(genre);
            System.out.println("Создан новый жанр с ID: " + genre.getId());
        }

        //создаём книгу
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(author);        // ← УСТАНОВИТЬ ССЫЛКУ НА АВТОРА
        book.setGenre(genre);          // ← УСТАНОВИТЬ ССЫЛКУ НА ЖАНР
        book.setPublicationYear(request.getPublicationYear());
        book.setIsbn(request.getIsbn());
        book.setDescription(request.getDescription());
        book.setPageCount(request.getPageCount());
        book.setAverageRating(0.0);

        System.out.println("Сохраняю книгу с author_id=" + author.getId() + ", genre_id=" + genre.getId());

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


    public List<BookResponse> searchBooks(String query) {
        //поиск по названию регистронезависимый
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(query);

        List<BookResponse> responses = new ArrayList<>();
        for (Book book : books) {
            responses.add(mapToResponse(book));
        }
        return responses;
    }

    //не забыть убрать логирование в консоль, после отладки
    public List<BookResponse> getBooksByAuthor(Long authorId) {
        //получить все книги автора
        System.out.println("Поиск книг автора ID: " + authorId);

        //проверим, есть ли такой автор
        Author author = authorRepository.findById(authorId).orElse(null);
        System.out.println("Автор найден: " + (author != null ? author.getName() : "null"));

        List<Book> books = bookRepository.findByAuthorId(authorId);
        System.out.println("Найдено книг: " + books.size());

        //если книг нет - создадим тестовую
        if (books.isEmpty()) {
            System.out.println("Создаю тестовую книгу для автора " + authorId);

            Book testBook = new Book();
            testBook.setTitle("Тестовая книга автора " + authorId);
            testBook.setPublicationYear(2025);

            //автор и жанр
            if (author == null) {
                author = new Author();
                author.setName("Автор " + authorId);
                author = authorRepository.save(author);
            }
            testBook.setAuthor(author);

            //жанр по-умолчанию
            Genre genre = genreRepository.findById(1L).orElse(null);
            if (genre == null) {
                genre = new Genre();
                genre.setName("Тестовый жанр");
                genre = genreRepository.save(genre);
            }
            testBook.setGenre(genre);

            testBook = bookRepository.save(testBook);
            books = List.of(testBook);
        }

        //в ответ в цикле
        List<BookResponse> responses = new ArrayList<>();
        for (Book book : books) {
            System.out.println("Добавлена книга: " + book.getTitle());
            responses.add(mapToResponse(book));
        }

        return responses;
    }

    public List<BookResponse> getBooksByGenre(Long genreId) {
        //получить все книги жанра
        List<Book> books = bookRepository.findByGenreId(genreId);

        List<BookResponse> responses = new ArrayList<>();
        for (Book book : books) {
            responses.add(mapToResponse(book));
        }
        return responses;
    }


    //метод преобразует любой объект Book в BookResponse
    //не забыть добавить проверку на null
    private BookResponse mapToResponse(Book book) {
        System.out.println("mapToResponse для книги: " + book.getTitle());

        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());

        //автор
        if (book.getAuthor() != null) {
            response.setAuthorName(book.getAuthor().getName());
            System.out.println("Автор: " + book.getAuthor().getName());
        } else {
            response.setAuthorName("Неизвестен");
            System.out.println("Автор: null");
        }

        //жанр
        if (book.getGenre() != null) {
            response.setGenreName(book.getGenre().getName());
            System.out.println("Жанр: " + book.getGenre().getName());
        } else {
            response.setGenreName("Неизвестен");
            System.out.println("Жанр: null");
        }

        //остальные поля
        response.setPublicationYear(book.getPublicationYear());
        response.setIsbn(book.getIsbn());
        response.setDescription(book.getDescription());
        response.setAverageRating(book.getAverageRating());
        response.setPageCount(book.getPageCount());

        System.out.println("Создан response: " + response.getTitle());
//        BookResponse response = new BookResponse();
//        response.setId(book.getId());
//        response.setTitle(book.getTitle());
//        response.setAuthorName("Автор"); //заглушка
//        response.setGenreName("Жанр"); //заглушка
//        response.setPublicationYear(book.getPublicationYear());
//        response.setIsbn(book.getIsbn());
//        response.setDescription(book.getDescription());
//        response.setAverageRating(book.getAverageRating());
//        response.setPageCount(book.getPageCount());
        return response;
    }
}
