package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("{course}")
    public Course getCourse(@PathVariable String course){
        return courseService.getCourse(course);

    }

    @GetMapping
    public List<Course> getCourses(@RequestParam(required = false) Course.Status status){
        return courseService.getCourses(status);
    }

    @PostMapping
    public Course addCourse(@Valid  @RequestBody Course course){

        System.out.println("POST mapping CourseService");

        return courseService.addCourse(course);
    }


    // delete 1
    @DeleteMapping("{course}")
    public void deleteCourse(@PathVariable String course){
        courseService.deleteCourse(course);
    }


    // put 2
    @PutMapping("{course}")
    public Course putCourse(@PathVariable String course, @RequestBody Course courseBody){
        return courseService.putCourse(course, courseBody);
    }

    // delete this

    // new test

    // patch 3
    @PatchMapping("{course}")
    public Course patchCourse(@PathVariable String course, @RequestBody Course courseBody){
        return courseService.patchCourse(course, courseBody);
    }

    // tekst test



}
