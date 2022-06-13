package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Semester;
import com.romantulchak.virtualuniversity.model.Views;

import java.time.LocalDate;

public class SemesterDTO {

    @JsonView({Views.SemesterView.class, Views.SpecializationView.class,Views.TeacherStudentGrades.class, Views.StudentGrades.class, Views.StudentGroupView.class})
    private long id;

    @JsonView({Views.SemesterView.class,Views.SpecializationView.class,Views.TeacherStudentGrades.class, Views.StudentGrades.class, Views.StudentGroupView.class})
    private String name;


    @JsonView({Views.SemesterView.class,Views.SpecializationView.class,Views.TeacherStudentGrades.class, Views.StudentGrades.class, Views.StudentGroupView.class})
    private int semesterNumber;

    @JsonView({Views.SemesterView.class,Views.SpecializationView.class,Views.TeacherStudentGrades.class, Views.StudentGrades.class, Views.StudentGroupView.class})
    private LocalDate startDate;

    @JsonView({Views.SemesterView.class,Views.SpecializationView.class,Views.TeacherStudentGrades.class, Views.StudentGrades.class, Views.StudentGroupView.class})
    private LocalDate endDate;

    public SemesterDTO() {
    }

    public SemesterDTO(Semester semester) {
        this.id = semester.getId();
        this.name = semester.getName();
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

    public int getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(int semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
