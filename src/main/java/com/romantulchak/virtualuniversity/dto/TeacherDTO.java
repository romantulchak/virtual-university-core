package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.model.Views;

public class TeacherDTO extends UserDTO{

    public TeacherDTO(){}

    public TeacherDTO(Teacher teacher) {
        super(teacher.getId(), teacher.getFirstName(), teacher.getLastName(), teacher.getPrivateEmail(), teacher.getEmail(),teacher.getGender());
    }
    @JsonView({Views.SemesterView.class, Views.StudentGrades.class,Views.TeacherStudentGrades.class,Views.SubjectView.class, Views.StudentGroupView.class})
    @Override
    public long getId() {
        return super.getId();
    }

    @JsonView({Views.SemesterView.class,Views.StudentGrades.class,Views.TeacherStudentGrades.class,Views.SubjectView.class, Views.StudentGroupView.class})
    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @JsonView({Views.SemesterView.class,Views.StudentGrades.class,Views.TeacherStudentGrades.class,Views.SubjectView.class, Views.StudentGroupView.class})
    @Override
    public String getLastName() {
        return super.getLastName();
    }
}
