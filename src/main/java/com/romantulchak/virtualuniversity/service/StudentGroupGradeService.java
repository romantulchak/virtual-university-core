package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.StudentGroupGradeDTO;
import com.romantulchak.virtualuniversity.model.StudentGroupGrade;

import java.util.Collection;

public interface StudentGroupGradeService {

    void setGrade(Collection<StudentGroupGrade> studentGroupGrade);

    Collection<StudentGroupGradeDTO> findStudentGradesBySubjectAndGroupForTeacher(long teacherId, long groupId, long subjectId, long semesterId);

    Collection<StudentGroupGradeDTO> findStudentGrades(long studentId, long semesterId);

    double findGradeForStudentBySubject(long groupId, long studentId, long subjectId, long semesterId);

    byte[] exportStudentGrades(long studentId, long semesterId);
}
