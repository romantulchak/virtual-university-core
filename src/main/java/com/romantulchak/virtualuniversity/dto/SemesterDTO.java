package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Semester;
import com.romantulchak.virtualuniversity.model.Subject;
import com.romantulchak.virtualuniversity.model.Views;

import java.util.Collection;
import java.util.stream.Collectors;

public class SemesterDTO {

    @JsonView(Views.SemesterView.class)
    private long id;

    @JsonView(Views.SemesterView.class)
    private String name;

    @JsonView(Views.SemesterView.class)
    private Collection<SubjectDTO> subjects;

    @JsonView(Views.SemesterView.class)
    private int semesterNumber;

    public SemesterDTO(Semester semester) {
        this.id = semester.getId();
        this.name = semester.getName();
        this.subjects = semester.getSubjects().stream().map(SubjectDTO::new).collect(Collectors.toList());
        this.semesterNumber = semester.getSemesterNumber();
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

    public Collection<SubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(Collection<SubjectDTO> subjects) {
        this.subjects = subjects;
    }

    public int getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(int semesterNumber) {
        this.semesterNumber = semesterNumber;
    }
}