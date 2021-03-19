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

    @OneToMany(mappedBy = "semester")
    private Collection<StudentGroup> groups;

    private int semesterNumber;

    @ManyToMany(mappedBy = "semesters")
    private Collection<Specialization> specialization;


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

    public Collection<StudentGroup> getGroups() {
        return groups;
    }

    public void setGroups(Collection<StudentGroup> groups) {
        this.groups = groups;
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
