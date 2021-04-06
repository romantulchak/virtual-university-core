package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.StudentDTO;
import com.romantulchak.virtualuniversity.dto.StudentGroupGradeDTO;
import com.romantulchak.virtualuniversity.dto.SubjectTeacherGroupDTO;
import com.romantulchak.virtualuniversity.model.StudentGroupGrade;
import com.romantulchak.virtualuniversity.projection.StudentGradeLimitedStudent;
import com.romantulchak.virtualuniversity.projection.StudentGradeLimitedTeacher;
import com.romantulchak.virtualuniversity.repository.StudentGroupGradeRepository;
import com.romantulchak.virtualuniversity.service.StudentGroupGradeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class StudentGroupGradeServiceImpl implements StudentGroupGradeService {
    private final StudentGroupGradeRepository studentGroupGradeRepository;

    public StudentGroupGradeServiceImpl(StudentGroupGradeRepository studentGroupGradeRepository) {
        this.studentGroupGradeRepository = studentGroupGradeRepository;
    }


    @Override
    public void setGrade(Collection<StudentGroupGrade> studentGroupGrade) {
        if (studentGroupGrade != null) {
            for (StudentGroupGrade groupGrade : studentGroupGrade) {
                studentGroupGradeRepository.setGrade(groupGrade.getId(), groupGrade.getGrade());
            }

        }
    }

    @Override
    public Collection<StudentGroupGradeDTO> findStudentGradesBySubjectAndGroupForTeacher(long teacherId, long groupId, long subjectId) {
        Collection<StudentGradeLimitedTeacher> gradesProjection = studentGroupGradeRepository.findStudentGradesForGroupAndSubjectByTeacher(groupId, subjectId);
        return getStudentGroupGradeDTOS(gradesProjection);
    }

    @Override
    public Collection<StudentGroupGradeDTO> findStudentGrades(long studentId) {
        Collection<StudentGradeLimitedStudent> studentGradesForStudent = studentGroupGradeRepository.findStudentGradesForStudent(studentId);
        return getGradesForStudent(studentGradesForStudent);
    }

    private Collection<StudentGroupGradeDTO> getGradesForStudent(Collection<StudentGradeLimitedStudent> studentGradesForStudent) {
        Collection<StudentGroupGradeDTO>grades = new ArrayList<>();
        for (StudentGradeLimitedStudent studentGradeLimitedStudent : studentGradesForStudent) {
            StudentGroupGradeDTO studentGroupGradeDTO = new StudentGroupGradeDTO.Builder(studentGradeLimitedStudent.getId())
                    .withGrade(studentGradeLimitedStudent.getGrade())
                    .withSubjectTeacherGroup(new SubjectTeacherGroupDTO(studentGradeLimitedStudent.getSubjectTeacherGroup()))
                    .build();
            grades.add(studentGroupGradeDTO);
        }
        return grades;
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
        return grades;
    }
}
