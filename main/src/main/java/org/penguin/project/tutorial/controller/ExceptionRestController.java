package org.penguin.project.tutorial.controller;

import org.penguin.project.tutorial.exception.ErrorCode;
import org.penguin.project.tutorial.model.response.BaseWebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionRestController {
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<BaseWebResponse> handleEntityNotFoundException() {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseWebResponse.error(ErrorCode.ENTITY_NOT_FOUND));
//    }
}
