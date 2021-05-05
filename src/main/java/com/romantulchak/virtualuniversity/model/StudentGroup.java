package com.romantulchak.virtualuniversity.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

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

    @ManyToOne
    private Semester semester;

    @OneToOne(mappedBy = "studentGroup")
    private Schedule schedule;

    @ManyToMany
    @JoinTable(name = "group_student", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students = new LinkedHashSet<>();

    @ManyToOne
    private Specialization specialization;

    @OneToMany(mappedBy = "studentGroup", cascade = CascadeType.REMOVE)
    private Set<SubjectTeacherGroup> subjectTeacherGroups = new LinkedHashSet<>();

    @OneToMany(mappedBy = "studentGroup", cascade = CascadeType.REMOVE)
    private Collection<StudentGroupGrade> studentGroupGrades;

    @OneToMany(mappedBy = "currentGroup", cascade = CascadeType.REMOVE)
    private Collection<Student> studentsInCurrentGroup;


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

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
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

    public Collection<Student> getStudentsInCurrentGroup() {
        return studentsInCurrentGroup;
    }

    public void setStudentsInCurrentGroup(Collection<Student> studentsInCurrentGroup) {
        this.studentsInCurrentGroup = studentsInCurrentGroup;
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
