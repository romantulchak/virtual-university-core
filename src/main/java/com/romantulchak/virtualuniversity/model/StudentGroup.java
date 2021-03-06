package com.romantulchak.virtualuniversity.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class StudentGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String name;

    @ManyToMany
    @JoinTable(name = "group_semester", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "semester_id"))
    private Collection<Semester> semesters;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Semester semester;

    @OneToMany(mappedBy = "studentGroup")
    private Set<Student> students = new LinkedHashSet<>();

    @ManyToOne
    private Specialization specialization;

    @OneToMany(mappedBy = "studentGroup", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<SubjectTeacherGroup> subjectTeacherGroups = new LinkedHashSet<>();

    @OneToMany(mappedBy = "studentGroup", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Collection<StudentGroupGrade> studentGroupGrades;

    public StudentGroup(){

    }

    public StudentGroup(long id, String name, Semester semester) {
        this.id = id;
        this.name = name;
        this.semester = semester;
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

    public Collection<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(Collection<Semester> semesters) {
        this.semesters = semesters;
    }

    public Collection<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<SubjectTeacherGroup> getSubjectTeacherGroups() {
        return subjectTeacherGroups;
    }

    public void setSubjectTeacherGroups(Set<SubjectTeacherGroup> subjectTeacherGroups) {
        this.subjectTeacherGroups = subjectTeacherGroups;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public Collection<StudentGroupGrade> getStudentGroupGrades() {
        return studentGroupGrades;
    }

    public void setStudentGroupGrades(Collection<StudentGroupGrade> studentGroupGrades) {
        this.studentGroupGrades = studentGroupGrades;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentGroup that = (StudentGroup) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
