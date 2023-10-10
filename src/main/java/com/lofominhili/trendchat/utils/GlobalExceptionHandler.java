package com.lofominhili.trendchat.utils;

import com.lofominhili.trendchat.dto.BasicDto.ErrorDTO;
import com.lofominhili.trendchat.exceptions.RequestDataValidationFailedException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception e) {
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handlePathVariableException(ConstraintViolationException e) {
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST.value(),
                RequestDataValidationFailedException.class.getSimpleName(),
                e.getMessage());
        return ResponseEntity.badRequest().body(errorDTO);
    }

    public static String handleValidationResults(BindingResult validationResult) {
        StringBuilder message = new StringBuilder("Validation errors:");
        for (FieldError error : validationResult.getFieldErrors()) {
            message.append(" \n").append(error.getField()).append(": ").append(error.getDefaultMessage());
        }
        return message.toString();
    }
}