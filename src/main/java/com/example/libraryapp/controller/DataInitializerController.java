package com.example.libraryapp.controller;

import com.example.libraryapp.entity.Author;
import com.example.libraryapp.entity.Book;
import com.example.libraryapp.entity.Genre;
import com.example.libraryapp.entity.Review;
import com.example.libraryapp.repository.AuthorRepository;
import com.example.libraryapp.repository.BookRepository;
import com.example.libraryapp.repository.GenreRepository;
import com.example.libraryapp.repository.ReviewRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test-data")
public class DataInitializerController {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    public DataInitializerController(AuthorRepository authorRepository,
                                     GenreRepository genreRepository,
                                     BookRepository bookRepository,
                                     ReviewRepository reviewRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostMapping("/fill")
    public Map<String, Object> fillTestData() {
        System.out.println("=== ЗАПОЛНЕНИЕ ТЕСТОВЫМИ ДАННЫМИ ===");

        Map<String, Object> result = new HashMap<>();

        try {
            //очистка (в обратном порядке из-за foreign keys)
            reviewRepository.deleteAll();
            bookRepository.deleteAll();
            authorRepository.deleteAll();
            genreRepository.deleteAll();

            //создание авторов (получаем реальные ID)
            List<Author> authors = new ArrayList<>();

            Author author1 = new Author();
            author1.setName("Лев Толстой");
            Author author2 = new Author();
            author2.setName("Фёдор Достоевский");
            Author author3 = new Author();
            author3.setName("Антон Чехов");
            Author author4 = new Author();
            author4.setName("Александр Пушкин");
            Author author5 = new Author();
            author5.setName("Михаил Булгаков");

            authors.add(authorRepository.save(author1));
            authors.add(authorRepository.save(author2));
            authors.add(authorRepository.save(author3));
            authors.add(authorRepository.save(author4));
            authors.add(authorRepository.save(author5));

            System.out.println("Создано авторов: " + authors.size());

            //создаём жанры
            List<Genre> genres = new ArrayList<>();

            Genre genre1 = new Genre();
            genre1.setName("Роман");
            genre1.setDescription("Большое повествовательное произведение");

            Genre genre2 = new Genre();
            genre2.setName("Драма");
            genre2.setDescription("Произведения с серьёзным сюжетом");

            Genre genre3 = new Genre();
            genre3.setName("Фантастика");
            genre3.setDescription("Научная фантастика и фэнтези");

            Genre genre4 = new Genre();
            genre4.setName("Детектив");
            genre4.setDescription("Детективные истории");

            Genre genre5 = new Genre();
            genre5.setName("Поэзия");
            genre5.setDescription("Стихотворные произведения");

            genres.add(genreRepository.save(genre1));
            genres.add(genreRepository.save(genre2));
            genres.add(genreRepository.save(genre3));
            genres.add(genreRepository.save(genre4));
            genres.add(genreRepository.save(genre5));

            System.out.println("Создано жанров: " + genres.size());

            //создаём книги (используем авторов и жанры)
            List<Book> books = new ArrayList<>();

            Book book1 = new Book();
            book1.setTitle("Война и мир");
            book1.setAuthor(authors.get(0)); // Лев Толстой
            book1.setGenre(genres.get(0));   // Роман
            book1.setPublicationYear(1869);
            book1.setIsbn("978-5-17-090194-4");
            book1.setDescription("Роман-эпопея о войне 1812 года");
            book1.setPageCount(1225);
            book1.setAverageRating(4.8);

            Book book2 = new Book();
            book2.setTitle("Преступление и наказание");
            book2.setAuthor(authors.get(1)); // Достоевский
            book2.setGenre(genres.get(0));   // Роман
            book2.setPublicationYear(1866);
            book2.setIsbn("978-5-17-090195-1");
            book2.setDescription("Роман о моральных дилеммах");
            book2.setPageCount(672);
            book2.setAverageRating(4.7);

            Book book3 = new Book();
            book3.setTitle("Анна Каренина");
            book3.setAuthor(authors.get(0)); // Лев Толстой
            book3.setGenre(genres.get(0));   // Роман
            book3.setPublicationYear(1877);
            book3.setIsbn("978-5-17-090196-8");
            book3.setDescription("Трагическая история любви");
            book3.setPageCount(864);
            book3.setAverageRating(4.6);

            Book book4 = new Book();
            book4.setTitle("Вишневый сад");
            book4.setAuthor(authors.get(2)); // Чехов
            book4.setGenre(genres.get(1));   // Драма
            book4.setPublicationYear(1904);
            book4.setIsbn("978-5-17-090197-5");
            book4.setDescription("Пьеса о смене эпох");
            book4.setPageCount(96);
            book4.setAverageRating(4.3);

            Book book5 = new Book();
            book5.setTitle("Евгений Онегин");
            book5.setAuthor(authors.get(3)); // Пушкин
            book5.setGenre(genres.get(4));   // Поэзия
            book5.setPublicationYear(1833);
            book5.setIsbn("978-5-17-090198-2");
            book5.setDescription("Роман в стихах");
            book5.setPageCount(288);
            book5.setAverageRating(4.5);

            Book book6 = new Book();
            book6.setTitle("Мастер и Маргарита");
            book6.setAuthor(authors.get(4)); // Булгаков
            book6.setGenre(genres.get(2));   // Фантастика
            book6.setPublicationYear(1967);
            book6.setIsbn("978-5-17-090199-9");
            book6.setDescription("Мистический роман");
            book6.setPageCount(480);
            book6.setAverageRating(4.9);

            books.add(bookRepository.save(book1));
            books.add(bookRepository.save(book2));
            books.add(bookRepository.save(book3));
            books.add(bookRepository.save(book4));
            books.add(bookRepository.save(book5));
            books.add(bookRepository.save(book6));

            System.out.println("Создано книг: " + books.size());

            //создаём отзывы (используем сохраненные книги)
            List<Review> reviews = new ArrayList<>();

            Review review1 = new Review();
            review1.setBook(books.get(0)); // Война и мир
            review1.setReviewerName("Иван Иванов");
            review1.setRating(5);
            review1.setComment("Великая книга! Перечитываю каждый год.");
            review1.setReviewDate(LocalDate.of(2024, 1, 15));

            Review review2 = new Review();
            review2.setBook(books.get(0)); // Война и мир
            review2.setReviewerName("Мария Петрова");
            review2.setRating(4);
            review2.setComment("Очень длинная, но интересная");
            review2.setReviewDate(LocalDate.of(2024, 2, 20));

            Review review3 = new Review();
            review3.setBook(books.get(1)); // Преступление и наказание
            review3.setReviewerName("Алексей Сидоров");
            review3.setRating(5);
            review3.setComment("Потрясающая психологическая глубина");
            review3.setReviewDate(LocalDate.of(2024, 3, 10));

            Review review4 = new Review();
            review4.setBook(books.get(5)); // Мастер и Маргарита
            review4.setReviewerName("Ольга Ковалёва");
            review4.setRating(5);
            review4.setComment("Лучшая книга 20 века!");
            review4.setReviewDate(LocalDate.of(2024, 4, 5));

            Review review5 = new Review();
            review5.setBook(books.get(5)); // Мастер и Маргарита
            review5.setReviewerName("Дмитрий Новиков");
            review5.setRating(4);
            review5.setComment("Сложно с первого раза, но гениально");
            review5.setReviewDate(LocalDate.of(2024, 5, 12));

            reviews.add(reviewRepository.save(review1));
            reviews.add(reviewRepository.save(review2));
            reviews.add(reviewRepository.save(review3));
            reviews.add(reviewRepository.save(review4));
            reviews.add(reviewRepository.save(review5));

            System.out.println("Создано отзывов: " + reviews.size());

            //результат
            result.put("success", true);
            result.put("message", "Тестовые данные созданы успешно!");
            result.put("authors", authors.size());
            result.put("genres", genres.size());
            result.put("books", books.size());
            result.put("reviews", reviews.size());

        } catch (Exception e) {
            System.out.println("Ошибка при создании тестовых данных: " + e.getMessage());
            e.printStackTrace();

            result.put("success", false);
            result.put("message", "Ошибка: " + e.getMessage());
        }

        return result;
    }

    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();

        status.put("authors", authorRepository.count());
        status.put("genres", genreRepository.count());
        status.put("books", bookRepository.count());
        status.put("reviews", reviewRepository.count());

        return status;
    }

    @PostMapping("/clear")
    public Map<String, Object> clearAllData() {
        System.out.println("=== ОЧИСТКА ВСЕХ ДАННЫХ ===");

        Map<String, Object> result = new HashMap<>();

        try {
            reviewRepository.deleteAll();
            bookRepository.deleteAll();
            authorRepository.deleteAll();
            genreRepository.deleteAll();

            result.put("success", true);
            result.put("message", "Все данные удалены");

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Ошибка: " + e.getMessage());
        }

        return result;
    }
}