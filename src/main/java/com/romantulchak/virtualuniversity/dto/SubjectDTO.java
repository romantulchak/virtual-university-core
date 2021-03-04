package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Subject;
import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.model.enumes.SubjectType;

import java.util.Collection;
import java.util.stream.Collectors;

public class SubjectDTO {
    @JsonView({Views.SubjectView.class,Views.SemesterView.class, Views.StudentGrades.class})
    private long id;

    @JsonView({Views.SubjectView.class, Views.SemesterView.class, Views.SpecializationView.class, Views.StudentGrades.class})
    private String name;

    @JsonView({Views.SubjectView.class,Views.SemesterView.class, Views.SpecializationView.class, Views.StudentGrades.class})
    private SubjectType type;
   
    @JsonView({Views.SubjectView.class,Views.SemesterView.class, Views.SpecializationView.class})
    private Collection<TeacherDTO> teachers;
    
    public SubjectDTO() {
    }

    public SubjectDTO(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.type = subject.getType();
        this.teachers = subject.getTeachers().stream().map(TeacherDTO::new).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubjectType getType() {
        return type;
    }

    public void setType(SubjectType type) {
        this.type = type;
    }

    public Collection<TeacherDTO> getTeachers() {
        return teachers;
    }

    public void setTeachers(Collection<TeacherDTO> teachers) {
        this.teachers = teachers;
    }
}
