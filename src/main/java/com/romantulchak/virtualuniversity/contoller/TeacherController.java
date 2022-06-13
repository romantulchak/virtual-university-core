package com.romantulchak.virtualuniversity.contoller;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.dto.TeacherDTO;
import com.romantulchak.virtualuniversity.model.Subject;
import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.payload.request.ResetPasswordRequest;
import com.romantulchak.virtualuniversity.service.impl.TeacherServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/teacher")
public class TeacherController {

    private final TeacherServiceImpl teacherService;

    public TeacherController(TeacherServiceImpl teacherService){
        this.teacherService = teacherService;
    }

    @PostMapping("/create-teacher")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public void createTeacher(@RequestBody Teacher teacher){
        teacherService.create(teacher);
    }

    @PutMapping("/reset-password")
    @PreAuthorize("hasRole('TEACHER') and @authComponent.hasPermission(authentication, #resetPasswordRequest.userId)")
    public void resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        teacherService.resetPassword(resetPasswordRequest);
    }
    @GetMapping("/find-teacher/{id}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('TEACHER') AND @authComponent.hasPermission(authentication, #id)")
    public TeacherDTO findTeacherById(@PathVariable("id") long id){
        return teacherService.findTeacherById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @JsonView(Views.TeacherView.class)
    public Collection<TeacherDTO> findAllTeachers(){
        return teacherService.findAllTeachers();
    }

    @PutMapping("/add-subjects-to-teacher/{id}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public void addSubjectsToTeacher(@RequestBody Collection<Subject> subjects, @PathVariable("id") long id){
        teacherService.addSubjectsToTeacher(id, subjects);
    }

    @GetMapping("/find-teachers-for-subject/{subjectId}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public Collection<TeacherDTO> findTeachersForSubject(@PathVariable("subjectId") long id){
       return teacherService.findTeachersBySubject(id);
    }
}
