package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Subject;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.model.enumes.SubjectType;

public class SubjectDTO {
    @JsonView(Views.SubjectView.class)
    private long id;

    @JsonView({Views.SubjectView.class, Views.SemesterView.class, Views.SpecializationView.class})
    private String name;

    @JsonView({Views.SubjectView.class,Views.SemesterView.class, Views.SpecializationView.class})
    private SubjectType type;
    public SubjectDTO() {
    }

    public SubjectDTO(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.type = subject.getType();
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
}
