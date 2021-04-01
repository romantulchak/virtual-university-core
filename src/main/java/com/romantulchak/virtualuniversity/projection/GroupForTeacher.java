package com.romantulchak.virtualuniversity.projection;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.*;

import java.util.Collection;

public interface GroupForTeacher {
    @JsonView(Views.StudentGroupView.class)
    long getId();

    @JsonView(Views.StudentGroupView.class)
    String getName();

    @JsonView(Views.StudentGroupView.class)
    int getCount();

    @JsonView(Views.StudentGroupView.class)
    Specialization getSpecialization();

    @JsonView(Views.StudentGroupView.class)
    Collection<Subject> getSubjects();

    @JsonView(Views.StudentGroupView.class)
    Collection<Student> getStudents();
}
