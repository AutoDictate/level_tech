package com.level.tech.exception.handler;

import com.level.tech.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleException(AlreadyExistsException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .message(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(UsernameNotFoundException exp) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(
                        ExceptionResponse.builder()
                                .message(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(BadCredentialException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .message(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            ex.printStackTrace();

            if (errorMessage != null && !errorMessage.isEmpty()) {
                // Return the first validation error with a business error description
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ExceptionResponse.builder()
                                .businessErrorDescription("Validation or Type mismatch error on : " + fieldName)
                                .message(errorMessage)
                                .build());
            }
        }
        // Fallback response if no field errors are found (unlikely scenario)
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .businessErrorDescription("Unknown validation error")
                        .message("Validation error")
                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(NotFoundException exp) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(
                        ExceptionResponse.builder()
                                .message(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(ExpiredJwtTokenException.class)
    public ResponseEntity<ExceptionResponse> handleExpiredJwtTokenException(ExpiredJwtTokenException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .message(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(EntityNotFoundException exp) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(
                        ExceptionResponse.builder()
                                .message(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<ExceptionResponse> handleException(FileProcessingException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .message(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
        exp.printStackTrace();
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorDescription("Internal error, please contact the admin")
                                .message(exp.getMessage())
                                .build()
                );
    }
}
