package com.romantulchak.virtualuniversity.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "semester_subject", joinColumns = @JoinColumn(name = "semester_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private Set<Subject> subjects;

    private int semesterNumber;


    @ManyToMany(mappedBy = "semesters")
    private Collection<Specialization> specialization;

    @OneToMany(mappedBy = "semester")
    private Collection<TeacherSubjectStudentGradeLink> teacherSubjectStudentGradeLinks;

    public Collection<Specialization> getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Collection<Specialization> specialization) {
        this.specialization = specialization;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Collection<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Semester)) return false;
        Semester semester = (Semester) o;
        return id == semester.id && semesterNumber == semester.semesterNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, semesterNumber);
    }
}
