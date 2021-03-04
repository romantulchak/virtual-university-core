package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.*;
import com.romantulchak.virtualuniversity.model.TeacherSubjectStudentGradeLink;
import com.romantulchak.virtualuniversity.repository.StudentGradeRepository;
import com.romantulchak.virtualuniversity.service.StudentGradesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
@Service
public class StudentGradesServiceImpl implements StudentGradesService {
    private final StudentGradeRepository studentGradeRepository;

    @Autowired
    public StudentGradesServiceImpl(StudentGradeRepository studentGradesService){
        this.studentGradeRepository = studentGradesService;
    }

    @Override
    public Collection<TeacherSubjectStudentGradeLinkDTO> findStudentSubjectsGrades(long id) {
        return studentGradeRepository.findAllByStudent_Id(id).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void createStudentGrades(Collection<TeacherSubjectStudentGradeLink> teacherSubjectStudentGradeLinks) {
        if (teacherSubjectStudentGradeLinks != null){
            studentGradeRepository.saveAll(teacherSubjectStudentGradeLinks);
        }
    }

    private TeacherSubjectStudentGradeLinkDTO convertToDTO(TeacherSubjectStudentGradeLink teacherSubjectStudentGradeLink){
        SubjectDTO subject = new SubjectDTO(teacherSubjectStudentGradeLink.getSubject());
        GradeDTO grade = new GradeDTO(teacherSubjectStudentGradeLink.getGrade() == null ? -1 : teacherSubjectStudentGradeLink.getGrade().getGrade().getValue());
        SpecializationDTO specialization = new SpecializationDTO(teacherSubjectStudentGradeLink.getSpecialization());
        TeacherDTO teacher = new TeacherDTO(teacherSubjectStudentGradeLink.getTeacher());
        return new TeacherSubjectStudentGradeLinkDTO(teacherSubjectStudentGradeLink.getId(),subject,grade,specialization, teacher);
    }
}
