package com.romantulchak.virtualuniversity.model;

import javax.persistence.*;

@Entity
public class TeacherSubjectStudentGradeLink {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
