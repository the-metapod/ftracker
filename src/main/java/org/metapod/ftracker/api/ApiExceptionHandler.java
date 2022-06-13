package org.metapod.ftracker.api;

import org.metapod.ftracker.exception.DataIntegrityException;
import org.metapod.ftracker.exception.ResourceMissingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler({DataIntegrityException.class})
    public ResponseEntity<String> handleDataIntegrityException(DataIntegrityException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler({ResourceMissingException.class})
    public ResponseEntity<String> handleResourceMissingException(ResourceMissingException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
