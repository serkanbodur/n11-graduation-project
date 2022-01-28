package com.example.n11graduationproject.exception;

public class CustomerIsNotExistException extends RuntimeException{
    public CustomerIsNotExistException(String message) {
        super(message);
    }
}
