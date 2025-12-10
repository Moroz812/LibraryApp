package com.example.libraryapp.repository;

import com.example.libraryapp.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorId(Long authorId);
    List<Book> findByGenreId(Long genreId);

    //ручной SQL запрос
//    @Query("SELECT b FROM Book b WHERE b.averageRating IS NOT NULL ORDER BY b.averageRating DESC")
//    List<Book> findTopRatedBooks();

    //топ книг - используем @Query или добавляем метод сортировки
    List<Book> findAllByOrderByAverageRatingDesc();
}
