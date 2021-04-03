package com.romantulchak.virtualuniversity.projection;

import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.model.SubjectTeacherGroup;

public interface StudentGradeLimited {
    long getId();

    Student getStudent();

    double getGrade();

}
