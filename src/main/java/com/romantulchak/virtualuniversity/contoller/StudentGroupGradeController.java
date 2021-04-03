package com.romantulchak.virtualuniversity.contoller;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.dto.StudentGroupGradeDTO;
import com.romantulchak.virtualuniversity.model.StudentGroupGrade;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.service.impl.StudentGroupGradeServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/student-group-grade")
public class StudentGroupGradeController {

    private StudentGroupGradeServiceImpl studentGroupGradeService;

    public StudentGroupGradeController(StudentGroupGradeServiceImpl studentGroupGradeService){
        this.studentGroupGradeService = studentGroupGradeService;
    }

    @PostMapping("/setGrade")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public void setGrade(@RequestBody Collection<StudentGroupGrade> studentGroupGrade){
        studentGroupGradeService.setGrade(studentGroupGrade);
    }

    @GetMapping("/findForTeacher")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER') OR @accessToStudentGroup.checkAccess(#teacherId)")
    @JsonView(Views.TeacherStudentGrades.class)
    public Collection<StudentGroupGradeDTO> findStudentGradesForTeacher(@RequestParam(value = "teacherId") long teacherId,@RequestParam(value = "groupId") long groupId, @RequestParam(value = "sbujectId") long subjectId){
        return studentGroupGradeService.findStudentGradesBySubjectAndGroupForTeacher(teacherId, groupId, subjectId);
    }

}
