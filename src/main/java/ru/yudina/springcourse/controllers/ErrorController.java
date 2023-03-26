package ru.yudina.springcourse.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.yudina.springcourse.model.ErrorInfo;

@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorInfo> handleException(RuntimeException exception) {
        ErrorInfo errorInfo = new ErrorInfo(exception.getMessage());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
