package com.romantulchak.virtualuniversity.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @ManyToMany
    @JoinTable(name = "specialization_subject", joinColumns = @JoinColumn(name = "specialization_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private Collection<Subject> subjects;

    @OneToMany(mappedBy = "specialization")
    private Collection<StudentGroup> studentGroups;

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

    public Collection<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Collection<Subject> subjects) {
        this.subjects = subjects;
    }

    public Collection<StudentGroup> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(Collection<StudentGroup> studentGroups) {
        this.studentGroups = studentGroups;
    }
}
