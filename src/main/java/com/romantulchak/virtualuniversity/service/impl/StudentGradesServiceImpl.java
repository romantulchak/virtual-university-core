package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.*;
import com.romantulchak.virtualuniversity.exception.StudentGradeNotFoundException;
import com.romantulchak.virtualuniversity.exception.StudentSubjectGradeAlreadyExists;
import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.model.TeacherSubjectStudentGradeLink;
import com.romantulchak.virtualuniversity.model.enumes.GradeRating;
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
        return studentGradeRepository.findAllByStudent_Id(id)
                .stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void createStudentGrades(Collection<TeacherSubjectStudentGradeLink> teacherSubjectStudentGradeLinks) {
        if (teacherSubjectStudentGradeLinks != null){
            studentGradeRepository.saveAll(teacherSubjectStudentGradeLinks);
        }
    }

    @Override
    public void setGrade(TeacherSubjectStudentGradeLink teacherSubjectStudentGradeLink) {
        TeacherSubjectStudentGradeLink teacherSubjectStudentGradeLink1 = studentGradeRepository.findById(teacherSubjectStudentGradeLink
                                                                                                .getId())
                                                                                                .orElseThrow(StudentGradeNotFoundException::new);
        if (teacherSubjectStudentGradeLink.getGrade() >= 2){
            throw new StudentSubjectGradeAlreadyExists(teacherSubjectStudentGradeLink.getSubject().getId());
        }
        studentGradeRepository.setGrade(teacherSubjectStudentGradeLink.getId(), teacherSubjectStudentGradeLink.getGrade());
    }

    @Override
    public void addStudentGradeTeacher(Teacher teacher, long id) {

    }

    @Override
    public Collection<TeacherSubjectStudentGradeLinkDTO> findStudentGradesForTeacher(long teacherId, long specializationId, long semesterId) {
        return studentGradeRepository.findAllByTeacher_IdAndSpecialization_IdAndSemester_Id(teacherId, specializationId, semesterId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TeacherSubjectStudentGradeLinkDTO convertToDTO(TeacherSubjectStudentGradeLink teacherSubjectStudentGradeLink){
        SubjectDTO subject = new SubjectDTO(teacherSubjectStudentGradeLink.getSubject());
        SpecializationDTO specialization = new SpecializationDTO(teacherSubjectStudentGradeLink.getSpecialization());
        TeacherDTO teacher = new TeacherDTO(teacherSubjectStudentGradeLink.getTeacher());
        StudentDTO student = new StudentDTO(teacherSubjectStudentGradeLink.getStudent());
        SemesterDTO semester = new SemesterDTO(teacherSubjectStudentGradeLink.getSemester());
        return new TeacherSubjectStudentGradeLinkDTO(teacherSubjectStudentGradeLink,subject,specialization, teacher, student, semester);
    }
}
