package com.example.demo.model;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class CourseMembers {

    @NotNull
    private LocalDateTime enrollmentDate;
    @NotNull
    private String email;

    public CourseMembers(@NotNull String email) {
        this.enrollmentDate = LocalDateTime.now();
        this.email = email;
    }
}
