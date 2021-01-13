package com.example.demo.exception;

public enum  CourseError {

    COURSE_NOT_FOUND("Course does not exist"),
    COURSE_IS_NOT_ACTIVE("Course is not active"),
    COURSE_ALREADY_EXISTS("Course already exists"),
    COURSE_START_DATE_IS_AFTER_END_DATE("Course start date is after end date"),
    COURSE_PARTICIPANTS_LIMIT_IS_EXCEEDED("COURSE_PARTICIPANTS_LIMIT_IS_EXCEEDED"),
    COURSE_CAN_NOT_SET_FULL_STATUS("COURSE_CAN_NOT_SET_FULL_STATUS"),
    STUDENT_IS_NOT_ACTIVE("Student is not Active"),
    STUDENT_ALREADY_ENROLLED("Student already enrolled on this course"),
    COURSE_CAN_NOT_SET_ACTIVE_STATUS("Course can not set Active status."+
                                             " Participants limit is equals participants number");

    private String message;

    CourseError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }



}
