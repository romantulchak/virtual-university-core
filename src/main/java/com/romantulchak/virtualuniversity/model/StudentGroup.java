package com.romantulchak.virtualuniversity.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Entity
public class StudentGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String name;

    @ManyToOne
    private Semester semester;

    @OneToOne
    private Schedule schedule;

    @ManyToMany
    @JoinTable(name = "group_student", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Collection<Student> students;

    //TODO: SubjectTeacherGroup

    @ManyToMany
    @JoinTable(name = "group_subject", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private Collection<Subject> subjects;


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

    public Collection<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Collection<Subject> subjects) {
        this.subjects = subjects;
    }
}
