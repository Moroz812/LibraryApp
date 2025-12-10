package com.example.libraryapp.controller;

import com.example.libraryapp.dto.ReviewRequest;
import com.example.libraryapp.dto.ReviewResponse;
import com.example.libraryapp.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books/{bookId}/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
        System.out.println("=== REVIEW CONTROLLER СОЗДАН ===");

    }

    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(
            @PathVariable Long bookId,
            @Valid @RequestBody ReviewRequest request) {

        System.out.println("=== POST /api/books/" + bookId + "/reviews ВЫЗВАН ===");
        System.out.println("Тело запроса: reviewer=" + request.getReviewerName() + ", rating=" + request.getRating());

        request.setBookId(bookId);
        ReviewResponse response = reviewService.addReview(request);

        System.out.println("Отзыв создан, ID: " + response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getBookReviews(@PathVariable Long bookId) {
        System.out.println("=== GET /api/books/" + bookId + "/reviews ВЫЗВАН ===");

        List<ReviewResponse> responses = reviewService.getBookReviews(bookId);
        System.out.println("Найдено отзывов: " + responses.size());

        return ResponseEntity.ok(responses);
    }
}