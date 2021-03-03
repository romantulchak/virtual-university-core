package com.romantulchak.virtualuniversity.contoller;

import com.romantulchak.virtualuniversity.dto.TeacherDTO;
import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.payload.request.ResetPasswordRequest;
import com.romantulchak.virtualuniversity.service.impl.TeacherServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
        teacherService.create(teacher);
    }

    @PutMapping("/restPassword")
    @PreAuthorize("hasRole('TEACHER') and @authComponent.hasPermission(authentication, #resetPasswordRequest.userId)")
    public void ResetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        teacherService.resetPassword(resetPasswordRequest);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public Collection<TeacherDTO> findAllTeachers(){
        return teacherService.findAllTeachers();
    }
}
