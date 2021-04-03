package com.romantulchak.virtualuniversity.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class StudentGroupGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private StudentGroup studentGroup;

    @ManyToOne(cascade = CascadeType.MERGE)
    private SubjectTeacherGroup subjectTeacherGroup;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Student student;

    private double grade;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public SubjectTeacherGroup getSubjectTeacherGroup() {
        return subjectTeacherGroup;
    }

    public void setSubjectTeacherGroup(SubjectTeacherGroup subjectTeacherGroup) {
        this.subjectTeacherGroup = subjectTeacherGroup;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
