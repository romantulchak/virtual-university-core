package com.romantulchak.virtualuniversity.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToMany(mappedBy = "semesters")
    private Collection<StudentGroup> groups;

    private int semesterNumber;

    private LocalDate startDate;

    private LocalDate endDate;

    @OneToMany(mappedBy = "semester", fetch = FetchType.LAZY)
    private Collection<Schedule> schedule;

    @OneToMany(mappedBy = "semester")
    private Collection<SubjectTeacherGroup> subjectTeacherGroups;

    @OneToMany(mappedBy = "semester")
    private Collection<StudentGroupGrade> studentGroupGrades;

    @OneToMany(mappedBy = "semester")
    private Collection<ScheduleDay> days;

    public Semester() {
    }

    public Semester(long id, String name, int semesterNumber) {
        this.id = id;
        this.name = name;
        this.semesterNumber = semesterNumber;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Collection<SubjectTeacherGroup> getSubjectTeacherGroups() {
        return subjectTeacherGroups;
    }

    public void setSubjectTeacherGroups(Collection<SubjectTeacherGroup> subjectTeacherGroups) {
        this.subjectTeacherGroups = subjectTeacherGroups;
    }

    public Collection<StudentGroupGrade> getStudentGroupGrades() {
        return studentGroupGrades;
    }

    public void setStudentGroupGrades(Collection<StudentGroupGrade> studentGroupGrades) {
        this.studentGroupGrades = studentGroupGrades;
    }

    public Collection<Schedule> getSchedule() {
        return schedule;
    }

    public Collection<ScheduleDay> getDays() {
        return days;
    }

    public void setDays(Collection<ScheduleDay> days) {
        this.days = days;
    }

    public void setSchedule(Collection<Schedule> schedule) {
        this.schedule = schedule;
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
