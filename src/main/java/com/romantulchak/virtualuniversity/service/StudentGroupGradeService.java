package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.StudentGroupGradeDTO;
import com.romantulchak.virtualuniversity.model.StudentGroupGrade;

import java.util.Collection;

public interface StudentGroupGradeService {

    void setGrade(Collection<StudentGroupGrade> studentGroupGrade);

    Collection<StudentGroupGradeDTO> findStudentGradesBySubjectAndGroupForTeacher(long teacherId, long groupId, long subjectId);

    Collection<StudentGroupGradeDTO> findStudentGrades(long studentId);

    double findGradeForStudentBySubject(long groupId, long studentId, long subjectId);
}
