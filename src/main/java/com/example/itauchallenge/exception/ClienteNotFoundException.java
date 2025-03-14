package com.example.itauchallenge.exception;

public class ClienteNotFoundException extends RuntimeException{
    public ClienteNotFoundException(String message) {
        super(message);
    }
}
