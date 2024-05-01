package com.example.usersbackend.api.exception.notfound;

import com.example.usersbackend.api.exception.BaseExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class NotFoundExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<Object> handleException(NotFoundException ex, WebRequest request) {
        return handleException(ex, request, HttpStatus.NOT_FOUND, ex.getMessage());
    }

}