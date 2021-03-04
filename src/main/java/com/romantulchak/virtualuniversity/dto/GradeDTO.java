package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Grade;
import com.romantulchak.virtualuniversity.model.Views;

public class GradeDTO {
    @JsonView(Views.StudentGrades.class)
    private double grade;

    public GradeDTO(double grade) {
        this.grade = grade;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
