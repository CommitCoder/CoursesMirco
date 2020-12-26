package com.example.demo.service;

import com.example.demo.exception.CourseError;
import com.example.demo.exception.CourseException;
import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceIml implements CourseService{

    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceIml(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getCourses(Course.Status status) {
        if(status!= null){
            return courseRepository.findAllByStatus(status);
        }
        return courseRepository.findAll();
    }

    @Override
    public Course getCourse(String course) {
        return courseRepository.findById(course).orElseThrow(() -> new RuntimeException("course problem CourseServiceIml"));
    }

    @Override
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(String course) {
        Course courseFromDb = courseRepository.findById(course)
                .orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));

        courseFromDb.setStatus(Course.Status.INACTIVE);
        courseRepository.save(courseFromDb);

    }

    @Override
    public Course putCourse(String course, Course courseBody) {

        return courseRepository.findById(course)
                .map(courseFromDb ->{
                    courseFromDb.setDescription("1");



                    return courseRepository.save(courseFromDb);
                }).orElseThrow(()-> new CourseException(CourseError.COURSE_NOT_FOUND));
//        return courseRepository.save(courseBody);
    }

    @Override
    public Course patchCourse(String course, Course courseBody) {
        return null;
    }
}
