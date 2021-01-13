package com.example.demo.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class StudentDto {

    //@NotBlank nie może być puste
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;

    @NotNull
    private Status status;

    public enum Status{
        ACTIVE,
        INACTIVE
    }

}
