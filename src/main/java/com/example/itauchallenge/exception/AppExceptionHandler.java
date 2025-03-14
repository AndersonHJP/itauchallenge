package com.example.itauchallenge.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Set;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException exception) {
        // Iterando sobre as violação de restrição para obter as informações
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        StringBuilder errorMessage = new StringBuilder("Campos inválidos:");

        for (ConstraintViolation<?> violation : violations) {
            // Construindo a mensagem com o nome do campo e a mensagem de erro
            errorMessage.append("\n- ").append(violation.getPropertyPath()).append(": ").append(violation.getMessage());
        }

        return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
    }
}
