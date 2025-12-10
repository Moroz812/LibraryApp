package com.example.libraryapp.service;

import com.example.libraryapp.dto.ReviewRequest;
import com.example.libraryapp.dto.ReviewResponse;
import com.example.libraryapp.entity.Book;
import com.example.libraryapp.entity.Review;
import com.example.libraryapp.exception.BookNotFoundException;
import com.example.libraryapp.exception.DuplicateReviewException;
import com.example.libraryapp.repository.BookRepository;
import com.example.libraryapp.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }

    public ReviewResponse addReview(ReviewRequest request) {
        System.out.println("=== ДОБАВЛЕНИЕ ОТЗЫВА ===");
        System.out.println("bookId: " + request.getBookId() +
                ", reviewer: " + request.getReviewerName() +
                ", rating: " + request.getRating());

        //книга существует?
        Book book = bookRepository.findById(request.getBookId()).orElse(null);
        if (book == null) {
            System.out.println("ОШИБКА: Книга не найдена, ID: " + request.getBookId());
            throw new BookNotFoundException("Книга не найдена");
        }
        System.out.println("Найдена книга: " + book.getTitle());

        //1 человек не оставляет 2 отзыва на книгу
        Optional<Review> existingReview = reviewRepository.findByBookIdAndReviewerName(
                request.getBookId(), request.getReviewerName());

        if (existingReview.isPresent()) {
            System.out.println("ОШИБКА: Пользователь уже оставлял отзыв");
            throw new DuplicateReviewException("Вы уже оставляли отзыв на эту книгу");
        }

        //создаём отзыв
        Review review = new Review();
        review.setBook(book);
        review.setReviewerName(request.getReviewerName());
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setReviewDate(LocalDate.now());

        System.out.println("Создаю отзыв для книги: " + book.getTitle());

        //сохраняем отзыв
        Review savedReview = reviewRepository.save(review);
        System.out.println("Отзыв сохранен с ID: " + savedReview.getId());

        //пересчитываем средний рейтинг книги
        updateBookAverageRating(book.getId());

        return mapToResponse(savedReview);
    }

    public List<ReviewResponse> getBookReviews(Long bookId) {
        System.out.println("=== ПОЛУЧЕНИЕ ОТЗЫВОВ ДЛЯ КНИГИ ID: " + bookId + " ===");

        //книга существует?
        if (!bookRepository.existsById(bookId)) {
            System.out.println("Книга не найдена: " + bookId);
            throw new BookNotFoundException("Книга не найдена");
        }

        //отзывы
        List<Review> reviews = reviewRepository.findByBookId(bookId);
        System.out.println("Найдено отзывов: " + reviews.size());

        //в ответ
        List<ReviewResponse> responses = new ArrayList<>();
        for (Review review : reviews) {
            responses.add(mapToResponse(review));
        }

        return responses;
    }

    private void updateBookAverageRating(Long bookId) {
        System.out.println("=== ПЕРЕСЧЕТ РЕЙТИНГА ДЛЯ КНИГИ ID: " + bookId + " ===");

        //получаем все отзывы для книги
        List<Review> reviews = reviewRepository.findByBookId(bookId);

        if (reviews.isEmpty()) {
            System.out.println("Нет отзывов, рейтинг = 0");
            Book book = bookRepository.findById(bookId).orElseThrow();
            book.setAverageRating(0.0);
            bookRepository.save(book);
            return;
        }

        //средний рейтинг
        double sum = 0;
        for (Review review : reviews) {
            sum += review.getRating();
        }
        double average = sum / reviews.size();

        System.out.println("Средний рейтинг: " + average + " (на основе " + reviews.size() + " отзывов)");

        //обновляем книгу
        Book book = bookRepository.findById(bookId).orElseThrow();
        book.setAverageRating(average);
        bookRepository.save(book);
    }

    private ReviewResponse mapToResponse(Review review) {
        System.out.println("Преобразование отзыва ID: " + review.getId());

        ReviewResponse response = new ReviewResponse();
        response.setId(review.getId());
        response.setReviewerName(review.getReviewerName());
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        response.setReviewDate(review.getReviewDate());

        //добавляем название книги
        if (review.getBook() != null) {
            response.setBookTitle(review.getBook().getTitle());
            System.out.println("Книга: " + review.getBook().getTitle());
        } else {
            response.setBookTitle("Неизвестная книга");
            System.out.println("Книга: null");
        }

        return response;
    }
}
