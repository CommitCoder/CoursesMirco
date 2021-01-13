package com.example.demo.model;


import com.example.demo.exception.CourseError;
import com.example.demo.exception.CourseException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
@Getter
@Setter
public class Course {

    // bedziemy wpisywac z palca @GeneratedValue nie jest potrzebne, ale nawet jeśli nie
    // wpiszemy to Spring wygeneruje to za nas
    @Id
    private String code;
    @NotBlank(message = "Field cannot be empty")
    private String name;
    private String description;
    @NotNull
    @Future
    private LocalDateTime startDate;
    @NotNull
    @Future
    private LocalDateTime endDate;

    @Max(value = 10,message = "participantsLimit = 10")
    private Long participantsLimit;

    @NotNull(message = "cant be null")
    @Min(0)
    private Long participantsNumber;

    @NotNull // Mongo nie musi mieć enumerated
    private Status status;


    public enum Status{
        ACTIVE,
        INACTIVE,
        FULL
    }

    private List<CourseMembers> courseMembers = new ArrayList<>();

    public void validateCourse(){
        validateCourseDate();
        validateParticipantsLimit();
        validateFullStatus();
    }

    public void incrementParticipantsNumber(){
        // zwiększamy o 1 participants number
        participantsNumber++;

        //jeśli participants number = participants limit ustaw status na FULL
        if(participantsNumber.equals(participantsLimit)){
            setStatus(Course.Status.FULL);
        }
    }

    private void validateCourseDate(){
        if(startDate.isAfter(endDate)){
            throw new CourseException(CourseError.COURSE_START_DATE_IS_AFTER_END_DATE);
        }
    }

    private void validateParticipantsLimit(){
        if(participantsNumber > participantsLimit){
            throw new CourseException(CourseError.COURSE_PARTICIPANTS_LIMIT_IS_EXCEEDED);
        }
    }

    private void validateFullStatus(){
        if(Status.FULL.equals(status) && !participantsNumber.equals(participantsLimit)){
            throw new CourseException(CourseError.COURSE_CAN_NOT_SET_FULL_STATUS);
        }
        if(Status.ACTIVE.equals(status) && participantsNumber.equals(participantsLimit) ){
            throw new CourseException(CourseError.COURSE_CAN_NOT_SET_ACTIVE_STATUS);
        }
    }



}
