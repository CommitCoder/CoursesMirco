package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

//    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
//    private final StudentServiceClient studentServiceClient;

    @PostMapping("/{courseCode}/student/{studentId}")
    public ResponseEntity<?> courseEnrollment(@PathVariable String courseCode, @PathVariable Long studentId ){
        courseService.courseEnrollment(courseCode, studentId);
        return ResponseEntity.ok().build();
    }


//    @GetMapping("test")
//    public List<Student> testFeignClient(){
//        return studentServiceClient.getStudents();
//    }

    @GetMapping("end")
    public String getString(){
        return "hello there";
    }

    @GetMapping("{courseCode}")
    public Course getCourse(@PathVariable String courseCode){
        System.out.println("getCourse Controller");
        return courseService.getCourse(courseCode);
    }

    @GetMapping
    public List<Course> getCourses(@RequestParam(required = false) Course.Status status){
        return courseService.getCourses(status);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // @Valid check if all fields from Course are set in json in body
//    public Course addCourse(@RequestBody Course course){
    public Course addCourse(@Valid @RequestBody Course course){
        course.validateCourse();
        return courseService.addCourse(course);
    }

    // delete 1
    @DeleteMapping("{courseCode}")
    public void deleteCourse(@PathVariable String courseCode){
        courseService.deleteCourse(courseCode);
    }

    // put 2
    @PutMapping("{courseCode}")
    public Course putCourse(@PathVariable String courseCode, @RequestBody Course courseBody){
        courseBody.validateCourse();
        return courseService.putCourse(courseCode, courseBody);
    }
    // delete this
    // new test
    // patch 3
    @PatchMapping("{courseCode}")
    public Course patchCourse(@PathVariable String courseCode, @RequestBody Course courseBody){
        System.out.println("patchCourse controller");
        courseBody.validateCourse();
        return courseService.patchCourse(courseCode, courseBody);
    }


}
