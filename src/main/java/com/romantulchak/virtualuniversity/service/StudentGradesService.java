package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.TeacherSubjectStudentGradeLinkDTO;
import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.model.TeacherSubjectStudentGradeLink;

import java.util.Collection;

public interface StudentGradesService {
    Collection<TeacherSubjectStudentGradeLinkDTO> findStudentSubjectsGrades(long id);
    Collection<TeacherSubjectStudentGradeLinkDTO> findStudentGradesForTeacher(long teacherId, long specializationId, long semesterId);
    void createStudentGrades(Collection<TeacherSubjectStudentGradeLink> teacherSubjectStudentGradeLinks);

    void setGrade(TeacherSubjectStudentGradeLink teacherSubjectStudentGradeLink);

    void addStudentGradeTeacher(Teacher teacher, long id);

    Collection<TeacherSubjectStudentGradeLinkDTO> findStudentGradesBySemester(long studentId, int semesterId);
}
