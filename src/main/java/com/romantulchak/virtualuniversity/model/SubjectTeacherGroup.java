package com.romantulchak.virtualuniversity.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@Accessors(chain = true)
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

    @OneToMany(mappedBy = "subjectTeacher", cascade = CascadeType.REMOVE)
    private Collection<Lesson> lessons;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "semester_id")
    private Semester semester;

    public SubjectTeacherGroup(){

    }

    public SubjectTeacherGroup(long id, Subject subject, Teacher teacher) {
        this.id = id;
        this.subject = subject;
        this.teacher = teacher;
    }
}
