package com.romantulchak.virtualuniversity.model;

import javax.persistence.*;

@Entity
public class StudentGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double grade;

    @ManyToOne
    private SubjectTeacherGroup subjectTeacherGroup;



    public StudentGrade(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public SubjectTeacherGroup getSubjectTeacherGroup() {
        return subjectTeacherGroup;
    }

    public void setSubjectTeacherGroup(SubjectTeacherGroup subjectTeacherGroup) {
        this.subjectTeacherGroup = subjectTeacherGroup;
    }

}
