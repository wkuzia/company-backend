package com.czarny.company_backend.domain.common;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleItemNotFound(ItemNotFoundException exception) {
        Map<String, String> response = new HashMap<>();
        response.put("error","Item not found");
        response.put("message", exception.getMessage());
        log.debug(exception.getMessage());
        return new ResponseEntity<>(response, NOT_FOUND);
    }

}
