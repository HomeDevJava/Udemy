package io.codemalone33.springboot.error.springboot_error.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import io.codemalone33.springboot.error.springboot_error.exceptions.UserNotFoundException;
import io.codemalone33.springboot.error.springboot_error.models.Error;

@RestControllerAdvice
public class HandlerExceptionController {

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<Error> divisionByZero(Exception e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setError("Division by zero");
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setDate(new Date());
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Error> notFound(NoResourceFoundException e) {
        Error error = new Error();
        error.setDate(new Date());
        error.setError("Api REST no encontrado ");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> numberFormatException(Exception e) {
        Map<String, Object> error = new HashMap<>();
        error.put("date",new Date());
        error.put("error","Error en el formato del numero, no se puede parsear");
        error.put("message",e.getMessage());
        error.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
        return error;
    }

    @ExceptionHandler({NullPointerException.class, HttpMessageNotWritableException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> nullPointerException(Exception e) {
        Map<String, Object> error = new HashMap<>();
        error.put("date",new Date());
        error.put("error","El usuario o role no existe");
        error.put("message",e.getMessage());
        error.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
        return error;
    }

}
