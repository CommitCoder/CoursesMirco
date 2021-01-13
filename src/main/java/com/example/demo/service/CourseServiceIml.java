package com.example.demo.service;

import com.example.demo.exception.CourseError;
import com.example.demo.exception.CourseException;
import com.example.demo.model.Course;
import com.example.demo.model.CourseMembers;
import com.example.demo.model.dto.StudentDto;
import com.example.demo.repository.CourseRepository;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CourseServiceIml implements CourseService{

    private final CourseRepository courseRepository;
    private final StudentServiceClient studentServiceClient;


    public CourseServiceIml(CourseRepository courseRepository, StudentServiceClient studentServiceClient) {
        this.courseRepository = courseRepository;
        this.studentServiceClient = studentServiceClient;
    }

    private void validateCourseExists(Course course) {
        courseRepository.existsByCode(course.getCode());
    }

    @Override
    public void courseEnrollment(String courseCode, Long studentId) {

        // najpierw sprawdzamy czy kurs istnieje
        Course course = getCourse(courseCode);
        validateCourseStatus(course);

        // później pobieramy studenta po jego id
        StudentDto student = studentServiceClient.getStudentById(studentId);

        validateStudentBeforeCourseEnrollment(course, student);

        // zwiekszamy liczbę uczestników o 1
        //jeśli participants number = participants limit ustaw status na FULL
        course.incrementParticipantsNumber();


        // teraz trzeba dodać tego uczesnitka do listy uczestnikow
        course.getCourseMembers().add(new CourseMembers(student.getEmail()));

        // zapisanie kursu na którym dokonywaliśmy operacji do bazy danych
        courseRepository.save(course);


    }

    private void validateStudentBeforeCourseEnrollment(Course course, StudentDto student) {
        // jeśli Student nie jest aktywny rzuć wyjątek
        if (!StudentDto.Status.ACTIVE.equals(student.getStatus())){
            throw new CourseException(CourseError.STUDENT_IS_NOT_ACTIVE);
        }

        //sprawdzamy czy w kursie nie ma już danego uczestnika
        if( course.getCourseMembers().stream()
                .anyMatch(member-> student.getEmail().equals(member.getEmail())) ){
            throw new CourseException(CourseError.STUDENT_ALREADY_ENROLLED); // 409 w exception hanler to do
        }
    }

    private void validateCourseStatus(Course course) {
        if(Course.Status.ACTIVE.equals(course.getStatus())){
            throw new CourseException(CourseError.COURSE_IS_NOT_ACTIVE);
        }
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
        Course courseFromDb = courseRepository.findById(course)
                .orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
        if(!Course.Status.ACTIVE.equals(courseFromDb.getStatus())){
            throw new CourseException(CourseError.COURSE_NOT_FOUND);
        }
        return courseFromDb;
    }

    @Override
    public Course addCourse(Course course) {
        System.out.println("1");
        if(courseRepository.existsByCode(course.getCode())){
            throw new CourseException(CourseError.COURSE_ALREADY_EXISTS);
        }
        validateCourseExists(course);
        course.validateCourse();
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
    public Course putCourse(String courseCode, Course courseBody) {
        return courseRepository.findById(courseCode)
                .map(courseFromDb ->{
                courseFromDb.setName(courseBody.getName());
                courseFromDb.setDescription(courseBody.getDescription());
                courseFromDb.setStartDate(courseBody.getStartDate());
                courseFromDb.setEndDate(courseBody.getEndDate());
                // participantsLimit participantsNumber Status
                courseFromDb.setParticipantsNumber(courseBody.getParticipantsNumber());
                courseFromDb.setParticipantsLimit(courseBody.getParticipantsLimit());
                courseFromDb.setStatus(courseBody.getStatus());
                    return courseRepository.save(courseFromDb);
                }).orElseThrow(()-> new CourseException(CourseError.COURSE_NOT_FOUND));
    }

    @Override
    public Course patchCourse(String courseCode, Course courseBody) {
        return courseRepository.findById(courseCode)
                .map(courseFromDb ->{
                    if(!StringUtils.isEmpty(courseBody.getName())){
                        courseFromDb.setName(courseBody.getName());
                    }
                    if(!StringUtils.isEmpty(courseBody.getDescription())){
                        courseFromDb.setDescription(courseBody.getDescription());
                    }
                    if(!StringUtils.isEmpty(courseBody.getStartDate())){
                        courseFromDb.setStartDate(courseBody.getStartDate());
                    }
                    if(!StringUtils.isEmpty(courseBody.getEndDate())){
                        courseFromDb.setEndDate(courseBody.getEndDate());
                    }
                    // PARTICIPANTS LIMIT / NUMBER
                    if(!StringUtils.isEmpty(courseBody.getParticipantsNumber())){
                        courseFromDb.setParticipantsNumber(courseBody.getParticipantsNumber());
                    }
                    if(!StringUtils.isEmpty( courseBody.getParticipantsLimit()) ){
                        courseFromDb.setParticipantsLimit(courseBody.getParticipantsLimit());
                    }
                    // PARTICIPANTS LIMIT / NUMBER
                    return courseRepository.save(courseFromDb);
                }).orElseThrow(()-> new CourseException(CourseError.COURSE_NOT_FOUND));
    }





}
