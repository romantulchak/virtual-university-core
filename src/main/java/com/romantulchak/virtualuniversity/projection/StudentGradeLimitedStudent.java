package com.romantulchak.virtualuniversity.projection;

import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.model.SubjectTeacherGroup;
import com.romantulchak.virtualuniversity.model.Teacher;

public interface StudentGradeLimitedStudent {
    long getId();

    SubjectTeacherGroup getSubjectTeacherGroup();

    double getGrade();


}
