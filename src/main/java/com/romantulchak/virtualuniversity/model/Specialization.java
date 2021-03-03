package com.romantulchak.virtualuniversity.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @ManyToOne
    private Course course;

    @ManyToMany(mappedBy = "specializations")
    private Collection<Student> students;

    @ManyToMany(mappedBy = "specializations")
    private Collection<Teacher> teachers;

    @OneToMany(mappedBy = "student")
    private Collection<TeacherSubjectStudentGradeLink> teacherSubjectStudentGradeLinks;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "specialization_semester", joinColumns = @JoinColumn(name = "specialization_id"), inverseJoinColumns = @JoinColumn(name = "semester_id"))
    @OrderBy("semesterNumber DESC ")
    private Collection<Semester> semesters;

    public Specialization(){

    }
    public Specialization(String name, Course course) {
        this.name = name;
        this.course = course;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Collection<Student> getStudents() {
        return students;
    }

    public void setStudents(Collection<Student> students) {
        this.students = students;
    }

    public Collection<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Collection<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Collection<TeacherSubjectStudentGradeLink> getTeacherSubjectStudentGradeLinks() {
        return teacherSubjectStudentGradeLinks;
    }

    public void setTeacherSubjectStudentGradeLinks(Collection<TeacherSubjectStudentGradeLink> teacherSubjectStudentGradeLinks) {
        this.teacherSubjectStudentGradeLinks = teacherSubjectStudentGradeLinks;
    }

    public Collection<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(Collection<Semester> semesters) {
        this.semesters = semesters;
    }
}
