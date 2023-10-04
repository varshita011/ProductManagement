package com.varshitha.productHandler;
import com.varshitha.productHandler.ErrorResponse;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@RestController
public class AppExceptionHandler {

//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
//        String message = "Invalid argument type: " + ex.getName();
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", message);
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, "Product Not Found", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
  
}
