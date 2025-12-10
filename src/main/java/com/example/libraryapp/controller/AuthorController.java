package com.example.libraryapp.controller;

import com.example.libraryapp.dto.AuthorRequest;
import com.example.libraryapp.dto.AuthorResponse;
import com.example.libraryapp.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@Valid @RequestBody AuthorRequest request) {
        AuthorResponse response = authorService.createAuthor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAllAuthor() {
        return ResponseEntity.ok(authorService.getAllAuthor());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorRequest request) {
        return ResponseEntity.ok(authorService.updateAuthor(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    //временный эндпоинт для проверки
//если вернет json - значит проблема в логике методов
//если вернет пусто - значит проблема в Spring/json
    @GetMapping("/test")
    public ResponseEntity<AuthorResponse> testEndpoint() {
        System.out.println("=== TEST ENDPOINT CALLED ===");

        AuthorResponse response = new AuthorResponse();
        response.setId(999L);
        response.setName("Тестовое имя");
        response.setBookCount(5);

        System.out.println("Returning: " + response.getName());
        return ResponseEntity.ok(response);
    }
}
