package com.compass.infraestructure.api.controllers;

import com.compass.domain.exceptions.DomainException;
import com.compass.domain.validation.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<?> handleDomainException(
            final DomainException ex
    ) {
        return ResponseEntity.unprocessableEntity().body(ApiError.from(ex));
    }

    static record ApiError(List<Error> errors, String message) {
        static ApiError from (final DomainException ex) {
            return new ApiError(ex.getErrors(), ex.getMessage());
        }
    }
}
