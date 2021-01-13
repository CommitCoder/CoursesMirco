package com.example.demo.service;

import com.example.demo.model.dto.StudentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// tak zdefioniowany jest studentService w EURECE, dodatkowo gdy będzie wiele instancji
// to feignclient z wykorzystaniem Ribbona będzie automatycznie przkierowywał do instancji najmniej obciążonej

//@RequestMapping("/students")
//@Component
@FeignClient(name = "STUDENT-SERVICE")
@RequestMapping("/students")
public interface StudentServiceClient {

    @GetMapping("/{studentId}")
    StudentDto getStudentById(@PathVariable Long studentId);

    @PostMapping("/emails")
    List<StudentDto> getStudentsByEmails(@RequestBody List<String> emails);

}


