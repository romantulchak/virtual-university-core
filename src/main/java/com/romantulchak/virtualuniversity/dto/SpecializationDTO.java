package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Semester;
import com.romantulchak.virtualuniversity.model.Specialization;
import com.romantulchak.virtualuniversity.model.StudentGroup;
import com.romantulchak.virtualuniversity.model.Views;

import java.util.Collection;
import java.util.stream.Collectors;

public class SpecializationDTO {
    @JsonView({Views.StudentView.class, Views.SpecializationView.class,Views.TeacherStudentGrades.class})
    private long id;

    @JsonView({Views.StudentView.class,Views.SpecializationView.class, Views.StudentGrades.class,Views.TeacherStudentGrades.class})
    private String name;

    @JsonView(Views.SpecializationView.class)
    private Collection<SemesterDTO> semesters;

    private Collection<StudentGroup> studentGroups;

    public SpecializationDTO(){

    }
    public SpecializationDTO(Specialization specialization){
        this.id = specialization.getId();
        this.name = specialization.getName();
    }
    public SpecializationDTO(Specialization specialization, Collection<Semester> semesters){
        this.id = specialization.getId();
        this.name = specialization.getName();
        this.semesters = semesters.stream().map(SemesterDTO::new).collect(Collectors.toList());
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

    public Collection<SemesterDTO> getSemesters() {
        return semesters;
    }

    public void setSemesters(Collection<SemesterDTO> semesters) {
        this.semesters = semesters;
    }

    public Collection<StudentGroup> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(Collection<StudentGroup> studentGroups) {
        this.studentGroups = studentGroups;
    }
}
