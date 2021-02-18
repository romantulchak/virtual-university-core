package com.romantulchak.virtualuniversity.contoller;

import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.service.TeacherService;
import com.romantulchak.virtualuniversity.service.impl.TeacherServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/teacher")
public class TeacherController {

    private TeacherServiceImpl teacherService;

    public TeacherController(TeacherServiceImpl teacherService){
        this.teacherService = teacherService;
    }

    @PostMapping("/createTeacher")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public void createTeacher(@RequestBody Teacher teacher){
        teacherService.createTeacher(teacher);
    }


}
