package com.example.libraryapp.repository;

import com.example.libraryapp.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    //для пагинации
    Page<Book> findAll(Pageable pageable);

    //пагинация с поиском по названию
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    //пагинация по автору
    Page<Book> findByAuthorId(Long authorId, Pageable pageable);

    //пагинация по жанру
    Page<Book> findByGenreId(Long genreId, Pageable pageable);

    //для улучшенной фильтрации sql запрос
    @Query("SELECT b FROM Book b WHERE " +
            "(:authorId IS NULL OR b.author.id = :authorId) AND " +
            "(:genreId IS NULL OR b.genre.id = :genreId) AND " +
            "(:minYear IS NULL OR b.publicationYear >= :minYear) AND " +
            "(:maxYear IS NULL OR b.publicationYear <= :maxYear) AND " +
            "(:minRating IS NULL OR b.averageRating >= :minRating)")
    List<Book> findWithFilters(
            @Param("authorId") Long authorId,
            @Param("genreId") Long genreId,
            @Param("minYear") Integer minYear,
            @Param("maxYear") Integer maxYear,
            @Param("minRating") Double minRating
    );
}
