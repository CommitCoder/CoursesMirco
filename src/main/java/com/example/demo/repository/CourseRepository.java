package com.example.demo.repository;

import com.example.demo.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


// this annotation can be added but doesnt have to since we are inheriting from MongoRepository
@Repository
public interface CourseRepository extends MongoRepository<Course, String> {


    List<Course> findAllByStatus(Course.Status status);


    boolean existsByCode(String code);
}
