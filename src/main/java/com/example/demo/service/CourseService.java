package com.example.demo.service;

import com.example.demo.model.Course;

import java.util.List;

public interface CourseService {
    List<Course> getCourses(Course.Status status);
    Course getCourse(String course);
    Course addCourse(Course course);
    void deleteCourse(String course);
    Course putCourse(String course, Course courseBody);
    Course patchCourse(String course, Course courseBody);

    void courseEnrollment(String courseCode, Long studentId);

}
