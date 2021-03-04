package com.romantulchak.virtualuniversity.model;

import com.romantulchak.virtualuniversity.model.enumes.SubjectType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private SubjectType type;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "subject_teacher", joinColumns = @JoinColumn(name = "subject_id"), inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Collection<Teacher> teachers;

    @OneToMany(mappedBy = "student")
    private Collection<TeacherSubjectStudentGradeLink> teacherSubjectStudentGradeLinks;

    @ManyToMany(mappedBy = "subjects")
    private Collection<Semester> semesters;


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

    public SubjectType getType() {
        return type;
    }

    public void setType(SubjectType type) {
        this.type = type;
    }

    public Collection<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Collection<Teacher> teachers) {
        this.teachers = teachers;
    }
}
