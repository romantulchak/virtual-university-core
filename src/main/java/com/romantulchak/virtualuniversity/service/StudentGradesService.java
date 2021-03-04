package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.TeacherSubjectStudentGradeLinkDTO;
import com.romantulchak.virtualuniversity.model.TeacherSubjectStudentGradeLink;

import java.util.Collection;

public interface StudentGradesService {
    Collection<TeacherSubjectStudentGradeLinkDTO> findStudentSubjectsGrades(long id);

    void createStudentGrades(Collection<TeacherSubjectStudentGradeLink> teacherSubjectStudentGradeLinks);
}
