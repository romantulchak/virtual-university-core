package com.romantulchak.virtualuniversity.model;

import com.romantulchak.virtualuniversity.model.enumes.LessonStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime dateStart;

    private LocalDateTime dateEnd;

    @ManyToOne
    private SubjectTeacherGroup subjectTeacher;

    @ManyToOne
    private ScheduleDay scheduleDay;

    @Enumerated(EnumType.STRING)
    private LessonStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public SubjectTeacherGroup getSubjectTeacher() {
        return subjectTeacher;
    }

    public void setSubjectTeacher(SubjectTeacherGroup subjectTeacher) {
        this.subjectTeacher = subjectTeacher;
    }

    public ScheduleDay getScheduleDay() {
        return scheduleDay;
    }

    public void setScheduleDay(ScheduleDay scheduleDay) {
        this.scheduleDay = scheduleDay;
    }

    public LessonStatus getStatus() {
        return status;
    }

    public void setStatus(LessonStatus status) {
        this.status = status;
    }
}
