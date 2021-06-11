package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.StudentDTO;
import com.romantulchak.virtualuniversity.dto.StudentGroupGradeDTO;
import com.romantulchak.virtualuniversity.dto.SubjectTeacherGroupDTO;
import com.romantulchak.virtualuniversity.exception.StudentGroupGradeEmptyException;
import com.romantulchak.virtualuniversity.exception.StudentNotFoundException;
import com.romantulchak.virtualuniversity.model.StudentGroupGrade;
import com.romantulchak.virtualuniversity.projection.StudentDataLimited;
import com.romantulchak.virtualuniversity.projection.StudentGradeLimitedStudent;
import com.romantulchak.virtualuniversity.projection.StudentGradeLimitedTeacher;
import com.romantulchak.virtualuniversity.repository.StudentGroupGradeRepository;
import com.romantulchak.virtualuniversity.repository.StudentRepository;
import com.romantulchak.virtualuniversity.service.StudentGroupGradeService;
import com.romantulchak.virtualuniversity.utils.ExportDataToPdf;
import com.romantulchak.virtualuniversity.utils.SubjectTeacherConverter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentGroupGradeServiceImpl implements StudentGroupGradeService {
    private final StudentGroupGradeRepository studentGroupGradeRepository;
    private final StudentRepository studentRepository;

    public StudentGroupGradeServiceImpl(StudentGroupGradeRepository studentGroupGradeRepository, StudentRepository studentRepository) {
        this.studentGroupGradeRepository = studentGroupGradeRepository;
        this.studentRepository = studentRepository;
    }


    @Override
    public void setGrade(Collection<StudentGroupGrade> studentGroupGrade) {
        if (studentGroupGrade != null && !studentGroupGrade.isEmpty()) {
            for (StudentGroupGrade groupGrade : studentGroupGrade) {
                studentGroupGradeRepository.updateGrade(groupGrade.getId(), groupGrade.getGrade());
            }
        } else {
            throw new StudentGroupGradeEmptyException("Student Group grade cannot be null");
        }
    }

    @Override
    public Collection<StudentGroupGradeDTO> findStudentGradesBySubjectAndGroupForTeacher(long teacherId, long groupId, long subjectId, long semesterId) {
        Collection<StudentGradeLimitedTeacher> gradesProjection = studentGroupGradeRepository.findStudentGradesForGroupAndSubjectByTeacher(groupId, subjectId, semesterId);
        return getStudentGroupGradeDTOS(gradesProjection);
    }

    @Override
    public Collection<StudentGroupGradeDTO> findStudentGrades(long studentId, long semesterId) {
        if (studentRepository.existsById(studentId)) {
            Collection<StudentGradeLimitedStudent> studentGradesForStudent = studentGroupGradeRepository.findGradesForStudent(studentId, semesterId);
            return getGradesForStudent(studentGradesForStudent);
        }
        throw new StudentNotFoundException(studentId);
    }

    private Collection<StudentGroupGradeDTO> getGradesForStudent(Collection<StudentGradeLimitedStudent> studentGradesForStudent) {
        Collection<StudentGroupGradeDTO> grades = new ArrayList<>();
        for (StudentGradeLimitedStudent studentGradeLimitedStudent : studentGradesForStudent) {
            SubjectTeacherGroupDTO subjectTeacherGroupDTO = SubjectTeacherConverter.getSubjectTeacherGroupDTO(studentGradeLimitedStudent.getId(),
                    studentGradeLimitedStudent
                            .getSubjectTeacherGroup()
                            .getSubject(),
                    studentGradeLimitedStudent.getSubjectTeacherGroup().getTeacher());
            StudentGroupGradeDTO studentGroupGradeDTO = new StudentGroupGradeDTO.Builder(studentGradeLimitedStudent.getId())
                    .withGrade(studentGradeLimitedStudent.getGrade())
                    .withSubjectTeacherGroup(subjectTeacherGroupDTO)
                    .build();
            grades.add(studentGroupGradeDTO);
        }
        return grades.stream()
                .sorted(Comparator.comparing(x -> x.getSubjectTeacherGroup().getSubject().getName()))
                .collect(Collectors.toList());
    }

    private Collection<StudentGroupGradeDTO> getStudentGroupGradeDTOS(Collection<StudentGradeLimitedTeacher> gradesProjection) {
        Collection<StudentGroupGradeDTO> grades = new ArrayList<>();
        for (StudentGradeLimitedTeacher grade : gradesProjection) {
            StudentGroupGradeDTO studentGrade = new StudentGroupGradeDTO.Builder(grade.getId())
                    .withStudent(new StudentDTO(grade.getStudent()))
                    .withGrade(grade.getGrade())
                    .build();
            grades.add(studentGrade);
        }
        return grades.stream().
                sorted()
                .collect(Collectors.toList());
    }

    @Override
    public double findGradeForStudentBySubject(long groupId, long studentId, long subjectId, long semesterId) {
        return studentGroupGradeRepository.findGradeForStudentBySubject(groupId, subjectId, studentId, semesterId)
                .orElseThrow(() -> new RuntimeException("Grade not found"));
    }

    @Override
    public byte[] exportStudentGrades(long studentId, long semesterId) {
        StudentDataLimited student = studentRepository.findStudentInformation(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));
        List<StudentGradeLimitedStudent> grades = new ArrayList<>(studentGroupGradeRepository.findGradesForStudent(studentId, semesterId))
                .stream()
                .sorted(Comparator.comparing(grade -> grade.getSubjectTeacherGroup().getSubject().getName()))
                .collect(Collectors.toList());
        return ExportDataToPdf.exportGradesForStudent(student, grades);
    }


}
