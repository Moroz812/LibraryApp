package com.example.libraryapp.service;

import com.example.libraryapp.dto.AuthorRequest;
import com.example.libraryapp.dto.AuthorResponse;
import com.example.libraryapp.entity.Author;
import com.example.libraryapp.entity.Book;
import com.example.libraryapp.exception.AuthorHasBooksException;
import com.example.libraryapp.exception.AuthorNotFoundException;
import com.example.libraryapp.exception.DuplicateAuthorException;
import com.example.libraryapp.repository.AuthorRepository;
import com.example.libraryapp.repository.BookRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }


    public AuthorResponse createAuthor(@Valid AuthorRequest request) throws DuplicateAuthorException {
        //проверка уникальности имени
        if (authorRepository.findByName(request.getName()).isPresent()) {
            throw new DuplicateAuthorException("Автор с именем '" + request.getName() + "' уже существует");
        }
        Author author = new Author();
        author.setName(request.getName());
        Author savedAuthor = authorRepository.save(author);
        return mapToResponse(savedAuthor);
    }


    public List<AuthorResponse> getAllAuthor() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorResponse> responses = new ArrayList<>();
        for (Author author : authors) {
            responses.add(mapToResponse(author));
        }
        return responses;
    }


    public AuthorResponse getAuthorById(Long id) {
        System.out.println("=== GET AUTHOR BY ID: " + id + " ===");

        Author author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            System.out.println("Автор не найден, создаю тестового...");
            author = new Author();
            author.setName("Тестовый автор " + id);
            author = authorRepository.save(author);
            System.out.println("Создан автор ID: " + author.getId());
            //throw new AuthorNotFoundException("Автор с ID " + id + " не найден");
        }
        //ответ
        AuthorResponse response = new AuthorResponse();
        response.setId(author.getId());
        response.setName(author.getName());

        //считаем книги
        List<Book> books = bookRepository.findByAuthorId(author.getId());
        response.setBookCount(books.size());

        System.out.println("Ответ: ID=" + response.getId() + ", Name=" + response.getName() + ", Books=" + response.getBookCount());

        return response;
        //return mapToResponse(author);
    }

    //доделать проверки
//    порядок для updateAuthor:
//    найти автора по ID (которого обновляем)
//    проверить, изменилось ли имя
//    если изменилось - проверить, нет ли другого автора с таким именем
//    сохранить изменения
    public AuthorResponse updateAuthor(Long id, @Valid AuthorRequest request) {
        //обновляем, без проверки уникальности
        Author author = authorRepository.findById(id).orElse(null);

        if (author == null) {
            throw new AuthorNotFoundException("Автор с ID " + id + " не найден");
        }

        //обновляем имя (даже если такое уже есть)
        author.setName(request.getName());
        Author updatedAuthor = authorRepository.save(author);

        return mapToResponse(updatedAuthor);
    }


    public void deleteAuthor(Long id) {
        //проверка существования
        if (!authorRepository.existsById(id)) {
            throw new AuthorNotFoundException("Автор с ID " + id + " не найден");
        }

        //проверить, есть ли книги у автора
        List<com.example.libraryapp.entity.Book> books = bookRepository.findByAuthorId(id);
        if (!books.isEmpty()) {
            throw new AuthorHasBooksException("Нельзя удалить автора, у которого есть книги. Количество книг: " + books.size());
        }

        //удаляем
        authorRepository.deleteById(id);
    }


    private AuthorResponse mapToResponse(Author author) {
        AuthorResponse response = new AuthorResponse();
        response.setId(author.getId());
        response.setName(author.getName());
        //книги автора
        List<Book> books = bookRepository.findByAuthorId(author.getId());
        response.setBookCount(books.size());

        return response;
    }
}