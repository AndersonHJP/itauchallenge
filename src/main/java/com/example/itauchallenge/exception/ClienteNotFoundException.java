package com.example.itauchallenge.exception;

public class ClienteNotFoundException extends RuntimeException{
    public ClienteNotFoundException(String message) {
        super(message);  // Passa a mensagem para a classe mãe (RuntimeException)
    }
}
