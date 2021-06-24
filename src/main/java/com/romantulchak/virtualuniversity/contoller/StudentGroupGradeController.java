package com.romantulchak.virtualuniversity.contoller;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.dto.StudentGroupGradeDTO;
import com.romantulchak.virtualuniversity.dto.pageable.PageableDTO;
import com.romantulchak.virtualuniversity.model.StudentGroupGrade;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.service.impl.StudentGroupGradeServiceImpl;
import org.springframework.http.MediaType;
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
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER') OR hasRole('TEACHER') AND @accessToStudentGroup.checkAccessToSetGrade(#studentGroupGrade, authentication)")
    public void setGrade(@RequestBody Collection<StudentGroupGrade> studentGroupGrade){
        studentGroupGradeService.setGrade(studentGroupGrade);
    }

    @GetMapping("/findForTeacher")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER') OR hasRole('TECHAER') AND @accessToStudentGroup.checkAccess(#teacherId)")
    @JsonView(Views.TeacherStudentGrades.class)
    public PageableDTO<Collection<StudentGroupGradeDTO>> findStudentGradesForTeacher(
                                                                                    @RequestParam(value = "teacherId") long teacherId,
                                                                                    @RequestParam(value = "groupId") long groupId,
                                                                                    @RequestParam(value = "subjectId") long subjectId,
                                                                                    @RequestParam(value = "semesterId") long semesterId,
                                                                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                                                                    @RequestParam(value = "size", defaultValue = "5") int size) {
        return studentGroupGradeService.findStudentGradesBySubjectAndGroupForTeacher(teacherId, groupId, subjectId, semesterId, page, size);
    }
    @GetMapping("/findGradesForChart")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER') OR hasRole('TECHAER') AND @accessToStudentGroup.checkAccess(#teacherId)")
    @JsonView(Views.TeacherStudentGrades.class)
    public Collection<StudentGroupGradeDTO> findStudentGradesForTeacher(
                                                                                    @RequestParam(value = "teacherId") long teacherId,
                                                                                    @RequestParam(value = "groupId") long groupId,
                                                                                    @RequestParam(value = "subjectId") long subjectId,
                                                                                    @RequestParam(value = "semesterId") long semesterId) {
        return studentGroupGradeService.findStudentGradesBySubjectAndGroupForTeacher(teacherId, groupId, subjectId, semesterId);
    }

    @GetMapping("/studentGrades")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER') OR hasRole('STUDENT') AND @authComponent.hasPermission(authentication,#studentId)")
    @JsonView(Views.SubjectGrade.class)
    public Collection<StudentGroupGradeDTO> findStudentGrades(@RequestParam(value = "studentId") long studentId, @RequestParam(value = "semesterId") long semesterId){
        return studentGroupGradeService.findStudentGrades(studentId, semesterId);
    }

    @GetMapping("/findGradeForStudentBySubject")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER') OR hasRole('STUDENT') AND @authComponent.hasPermission(authentication,#studentId)")
    public double findGradeForStudentBySubject(@RequestParam(value = "groupId") long groupId,
                                               @RequestParam(value = "studentId") long studentId,
                                               @RequestParam(value = "subjectId") long subjectId,
                                               @RequestParam(value = "semesterId") long semesterId){
        return studentGroupGradeService.findGradeForStudentBySubject(groupId, studentId, subjectId, semesterId);
    }
    @GetMapping(value = "/exportGradesForStudent/{studentId}/{semesterId}", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasRole('ADMIN') OR hasRole('STUDENT') AND @accessToStudentGroup.checkAccessToGrades(#studentId)")
    public byte[] exportGradesForStudent(@PathVariable("studentId") long studentId, @PathVariable("semesterId") long semesterId){
        return studentGroupGradeService.exportStudentGrades(studentId, semesterId);
    }

}
