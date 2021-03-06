package com.romantulchak.virtualuniversity.contoller;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.dto.TeacherSubjectStudentGradeLinkDTO;
import com.romantulchak.virtualuniversity.model.TeacherSubjectStudentGradeLink;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.service.StudentGradesService;
import com.romantulchak.virtualuniversity.service.impl.SemesterServiceImpl;
import com.romantulchak.virtualuniversity.service.impl.StudentGradesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/student-grades")
public class StudentGradeController {
    private final StudentGradesServiceImpl studentGradesService;

    @Autowired
    public StudentGradeController(StudentGradesServiceImpl studentGradesService){
        this.studentGradesService = studentGradesService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER') OR hasRole('STUDENT') AND @authComponent.hasPermission(authentication, #id)")
    @JsonView(Views.StudentGrades.class)
    public Collection<TeacherSubjectStudentGradeLinkDTO> findGradesForStudent(@PathVariable("id") long id){
        return studentGradesService.findStudentSubjectsGrades(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public void create(@RequestBody Collection<TeacherSubjectStudentGradeLink> teacherSubjectStudentGradeLinks){
        studentGradesService.createStudentGrades(teacherSubjectStudentGradeLinks);
    }
    @GetMapping("/findStudentGradesForTeacher/{teacherId}/{specId}/{semesterId}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('TEACHER') AND @authComponent.hasPermission(authentication, #teacherId)")
    @JsonView(Views.TeacherStudentGrades.class)
    public Collection<TeacherSubjectStudentGradeLinkDTO> findStudentGradesForTeacher(@PathVariable("teacherId") long teacherId,
                                                                                     @PathVariable("specId") long specializationId,
                                                                                     @PathVariable("semesterId") long semesterId){
        return studentGradesService.findStudentGradesForTeacher(teacherId, specializationId, semesterId);
    }

    @PutMapping("/setGradeForStudent")
    @PreAuthorize("hasRole('TEACHER') AND @authComponent.hasPermission(authentication, #teacherSubjectStudentGradeLink.teacher.id)")
    public void setGrade(@RequestBody TeacherSubjectStudentGradeLink teacherSubjectStudentGradeLink){
        studentGradesService.setGrade(teacherSubjectStudentGradeLink);
    }
}
