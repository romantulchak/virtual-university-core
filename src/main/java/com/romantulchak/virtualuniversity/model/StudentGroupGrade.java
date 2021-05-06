package com.romantulchak.virtualuniversity.model;

import com.romantulchak.virtualuniversity.model.enumes.GradeStatus;

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

    @Enumerated(EnumType.STRING)
    private GradeStatus status;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Semester semester;

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

    public GradeStatus getStatus() {
        return status;
    }

    public void setStatus(GradeStatus status) {
        this.status = status;
    }


    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }
}
