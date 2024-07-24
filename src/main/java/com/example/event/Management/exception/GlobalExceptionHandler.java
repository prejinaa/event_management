package com.example.event.Management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class GlobalExceptionHandler extends Throwable {
    //This annotation is used to specify the type of exception the method handles.

    @ExceptionHandler(EventNotFound.class)
    public ResponseEntity<ErrorResponse> handleEventNotFoundException(EventNotFound ex) {
         ErrorResponse errorResponse = new ErrorResponse("Resource not found", ex.getMessage());
         return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventAlreadyExist.class)
    public ResponseEntity<ErrorResponse> handleEventAlreadyExist(EventAlreadyExist ex) {
          ErrorResponse errorResponse = new ErrorResponse("Resource not found", ex.getMessage());
          return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(java.lang.Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(java.lang.Exception ex) {
          ErrorResponse errorResponse = new ErrorResponse("Internal Server Error", ex.getMessage());
          return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
