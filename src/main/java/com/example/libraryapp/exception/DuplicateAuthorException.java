package com.example.libraryapp.exception;

public class DuplicateAuthorException extends RuntimeException {
    public DuplicateAuthorException(String message) {
        super(message);
    }
}
