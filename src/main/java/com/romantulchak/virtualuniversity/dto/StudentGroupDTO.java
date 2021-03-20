package com.romantulchak.virtualuniversity.dto;

import com.romantulchak.virtualuniversity.model.*;

import javax.persistence.OneToMany;
import java.util.Collection;

public class StudentGroupDTO {

    private long id;

    private String name;

    private Semester semester;

    private Schedule schedule;

    private Collection<Student> students;

    private Collection<Specialization> specializations;

    private Collection<SubjectTeacherGroup> subjects;

    public StudentGroupDTO(){

    }

    public StudentGroupDTO(StudentGroup studentGroup) {
        this.id = studentGroup.getId();
        this.name = studentGroup.getName();
//        this.semester = group.getSemester();
        this.schedule = studentGroup.getSchedule();
        this.students = studentGroup.getStudents();
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

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
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

    public void setStudents(Collection<Student> students) {
        this.students = students;
    }

    public Collection<Specialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(Collection<Specialization> specializations) {
        this.specializations = specializations;
    }

    public Collection<SubjectTeacherGroup> getSubjects() {
        return subjects;
    }

    public void setSubjects(Collection<SubjectTeacherGroup> subjects) {
        this.subjects = subjects;
    }
}
