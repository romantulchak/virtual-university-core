package com.romantulchak.virtualuniversity.projection;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Semester;
import com.romantulchak.virtualuniversity.model.Specialization;
import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.model.Views;

import java.util.Collection;

public interface GroupStudentsLimited {
    @JsonView(Views.StudentGroupView.class)
    long getId();

    @JsonView(Views.StudentGroupView.class)
    String getName();

    @JsonView(Views.StudentGroupView.class)
    int getCount();

    @JsonView(Views.StudentGroupView.class)
    Semester getSemester();

    @JsonView(Views.StudentGroupView.class)
    Specialization getSpecialization();

    @JsonView(Views.StudentGroupView.class)
    Collection<Student> getStudents();

}
