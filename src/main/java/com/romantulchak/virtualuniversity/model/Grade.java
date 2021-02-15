package com.romantulchak.virtualuniversity.model;

import com.romantulchak.virtualuniversity.model.enumes.GradeRating;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated
    private GradeRating grade;

    @OneToMany(mappedBy = "student")
    private Collection<TeacherSubjectStudentGradeLink> teacherSubjectStudentGradeLinks;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GradeRating getGrade() {
        return grade;
    }

    public void setGrade(GradeRating grade) {
        this.grade = grade;
    }

    public Collection<TeacherSubjectStudentGradeLink> getTeacherSubjectStudentGradeLinks() {
        return teacherSubjectStudentGradeLinks;
    }

    public void setTeacherSubjectStudentGradeLinks(Collection<TeacherSubjectStudentGradeLink> teacherSubjectStudentGradeLinks) {
        this.teacherSubjectStudentGradeLinks = teacherSubjectStudentGradeLinks;
    }
}
