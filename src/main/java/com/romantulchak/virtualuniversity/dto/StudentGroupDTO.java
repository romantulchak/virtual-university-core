package com.romantulchak.virtualuniversity.dto;

import com.romantulchak.virtualuniversity.model.StudentGroup;
import com.romantulchak.virtualuniversity.model.Schedule;
import com.romantulchak.virtualuniversity.model.Semester;
import com.romantulchak.virtualuniversity.model.Student;

import java.util.Collection;

public class StudentGroupDTO {

    private long id;

    private String name;

    private Semester semester;

    private Schedule schedule;

    private Collection<Student> students;


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
}
