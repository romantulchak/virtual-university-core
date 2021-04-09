package com.romantulchak.virtualuniversity.model;

import com.romantulchak.virtualuniversity.dto.SubjectTeacherGroupDTO;
import com.romantulchak.virtualuniversity.model.enumes.SubjectType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class SubjectTeacherGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "studentGroup_id")
    private StudentGroup studentGroup;

    @OneToMany(mappedBy = "subjectTeacherGroup")
    private Collection<StudentGroupGrade> studentGrades;

    @OneToMany(mappedBy = "subjectTeacher")
    private Collection<Lesson> lessons;

    public SubjectTeacherGroup(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Collection<StudentGroupGrade> getStudentGrades() {
        return studentGrades;
    }

    public void setStudentGrades(Collection<StudentGroupGrade> studentGrades) {
        this.studentGrades = studentGrades;
    }

    public Collection<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Collection<Lesson> lessons) {
        this.lessons = lessons;
    }
}
