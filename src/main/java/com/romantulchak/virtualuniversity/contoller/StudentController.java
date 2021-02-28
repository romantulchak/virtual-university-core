package com.romantulchak.virtualuniversity.contoller;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.dto.StudentDTO;
import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.payload.request.ResetPasswordRequest;
import com.romantulchak.virtualuniversity.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/student")
public class StudentController {
    private final StudentServiceImpl studentService;
    @Autowired
    public StudentController(StudentServiceImpl studentService){
        this.studentService = studentService;
    }

    @GetMapping("/test")
    public Student getStudentGrade(){
        return studentService.studentGrade();
    }


    @PostMapping("/createStudent")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public void createUser(@RequestBody Student student){
        studentService.create(student);
    }


    @GetMapping("/getStudentInformation/{userId}")
    @JsonView(Views.StudentView.class)
    @PreAuthorize("hasRole('ADMIN') OR @authComponent.hasPermission(authentication, #id)")
    public StudentDTO getStudentInformation(@PathVariable("userId") long id){
        return studentService.getStudentInformation(id);
    }
    @PutMapping("/resetPassword")
    @PreAuthorize("hasRole('STUDENT') AND @authComponent.hasPermission(authentication, #resetPasswordRequest.userId)")
    public void resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        studentService.resetStudentPassword(resetPasswordRequest);
    }
}
