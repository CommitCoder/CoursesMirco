package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CourseExceptionHandler {

    public ResponseEntity<ErrorInfo> handleException(CourseException e){

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;


        // CourseError.COURSE_IS_NOT_ACTIVE.getMessage() or CourseError.COURSE_IS_NOT_ACTIVE difference ?

        if(CourseError.COURSE_IS_NOT_ACTIVE.getMessage().equals(e.getCourseError().getMessage())){
            httpStatus = HttpStatus.CONFLICT;
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo(e.getCourseError().getMessage()));


    }





}
