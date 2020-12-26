package com.example.demo.exception;

public enum  CourseError {

    COURSE_NOT_FOUND("Course does not exist"),
    COURSE_IS_NOT_ACTIVE("Course is not active");

    private String message;

    CourseError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }



}
