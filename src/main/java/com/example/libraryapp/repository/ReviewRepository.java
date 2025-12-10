package com.example.libraryapp.repository;

import com.example.libraryapp.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBookId(Long bookId);
    Optional<Review> findByBookIdAndReviewerName(Long bookId, String reviewerName);
    Double findAverageRatingByBookId(Long bookId);
}