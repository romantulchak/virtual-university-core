package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.*;

import javax.persistence.*;

public class StudentGroupGradeDTO {

    @JsonView(Views.TeacherStudentGrades.class)
    private long id;

    @JsonView(Views.TeacherStudentGrades.class)
    private StudentGroupDTO studentGroup;


    @JsonView(Views.TeacherStudentGrades.class)
    private SubjectTeacherGroupDTO subjectTeacherGroup;


    @JsonView(Views.TeacherStudentGrades.class)
    private StudentDTO student;

    @JsonView(Views.TeacherStudentGrades.class)
    private double grade;

    public StudentGroupGradeDTO(Builder builder){
        this.id = builder.id;
        this.studentGroup = builder.studentGroup;
        this.subjectTeacherGroup = builder.subjectTeacherGroup;
        this.student = builder.student;
        this.grade = builder.grade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StudentGroupDTO getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroupDTO studentGroup) {
        this.studentGroup = studentGroup;
    }

    public SubjectTeacherGroupDTO getSubjectTeacherGroup() {
        return subjectTeacherGroup;
    }

    public void setSubjectTeacherGroup(SubjectTeacherGroupDTO subjectTeacherGroup) {
        this.subjectTeacherGroup = subjectTeacherGroup;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public static class Builder{
        private long id;

        private StudentGroupDTO studentGroup;


        private SubjectTeacherGroupDTO subjectTeacherGroup;


        private StudentDTO student;

        private double grade;

        public Builder(long id){
            this.id = id;
        }

        public Builder withStudentGroup(StudentGroupDTO studentGroup){
            this.studentGroup = studentGroup;
            return this;
        }
        public Builder withSubjectTeacherGroup(SubjectTeacherGroupDTO subjectTeacherGroup){
            this.subjectTeacherGroup = subjectTeacherGroup;
            return this;
        }
        public Builder withStudent(StudentDTO student){
            this.student = student;
            return this;
        }
        public Builder withGrade(double grade){
            this.grade = grade;
            return this;
        }
        public StudentGroupGradeDTO build(){
            return new StudentGroupGradeDTO(this);
        }


    }



}
