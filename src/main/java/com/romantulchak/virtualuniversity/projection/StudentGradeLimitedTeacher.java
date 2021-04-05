package com.romantulchak.virtualuniversity.projection;

import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.model.SubjectTeacherGroup;

public interface StudentGradeLimitedTeacher {
    long getId();

    Student getStudent();

    double getGrade();

}
