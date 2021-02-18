package com.romantulchak.virtualuniversity.contoller;

import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.model.TeacherSubjectStudentGradeLink;
import com.romantulchak.virtualuniversity.payload.request.RegistrationRequest;
import com.romantulchak.virtualuniversity.service.StudentService;
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
        studentService.createStudent(student);
    }
}
