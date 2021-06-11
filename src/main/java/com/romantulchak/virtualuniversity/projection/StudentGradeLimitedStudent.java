package com.romantulchak.virtualuniversity.projection;

import com.romantulchak.virtualuniversity.model.SubjectTeacherGroup;

public interface StudentGradeLimitedStudent {
    long getId();

    SubjectTeacherGroup getSubjectTeacherGroup();

    double getGrade();

    void setId(long id);

    void setSubjectTeacherGroup(SubjectTeacherGroup subjectTeacherGroup);

    void setGrade(double grade);


}
