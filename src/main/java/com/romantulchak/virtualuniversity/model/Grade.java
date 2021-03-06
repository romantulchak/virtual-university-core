package com.romantulchak.virtualuniversity.model;

import com.romantulchak.virtualuniversity.model.enumes.GradeRating;
import com.romantulchak.virtualuniversity.utils.GradeConverter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

//    @Convert(converter = GradeConverter.class)
//    private GradeRating grade;

    private double grade = -1.0;
//    @OneToMany(mappedBy = "grade")
//    private Collection<TeacherSubjectStudentGradeLink> teacherSubjectStudentGradeLinks;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
//
//    public GradeRating getGrade() {
//        return grade;
//    }
//
//    public void setGrade(GradeRating grade) {
//        this.grade = grade;
//    }


    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }


}
