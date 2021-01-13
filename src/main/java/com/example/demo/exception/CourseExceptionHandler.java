package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CourseExceptionHandler {

    @ExceptionHandler(value = CourseException.class)
    public ResponseEntity<ErrorInfo> handleException(CourseException e){

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if(CourseError.COURSE_NOT_FOUND.getMessage().equals(e.getCourseError().getMessage())){
            httpStatus = HttpStatus.NOT_FOUND;

        }else if(CourseError.COURSE_START_DATE_IS_AFTER_END_DATE.equals(e.getCourseError())){
            httpStatus = HttpStatus.BAD_REQUEST;

        }else if(CourseError.COURSE_PARTICIPANTS_LIMIT_IS_EXCEEDED.equals(e.getCourseError())
        || CourseError.COURSE_CAN_NOT_SET_FULL_STATUS.equals(e.getCourseError())
        || CourseError.COURSE_CAN_NOT_SET_ACTIVE_STATUS.equals(e.getCourseError()) ) {
            httpStatus = HttpStatus.CONFLICT;
        }else if(CourseError.COURSE_ALREADY_EXISTS.equals(e.getCourseError()) ) {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
            // ResponseEntity  .body()  - sprawia, że mamy odpowiedz w formacie JSONA
        return ResponseEntity.status(httpStatus).body(new ErrorInfo(e.getCourseError().getMessage()));
    }






}
