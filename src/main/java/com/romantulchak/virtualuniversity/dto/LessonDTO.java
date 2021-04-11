package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.ScheduleDay;
import com.romantulchak.virtualuniversity.model.SubjectTeacherGroup;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.model.enumes.LessonStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Comparator;

public class LessonDTO implements Comparable<LessonDTO>{

    @JsonView(Views.ScheduleView.class)
    private long id;

    @JsonView(Views.ScheduleView.class)
    private LocalDateTime dateStart;

    @JsonView(Views.ScheduleView.class)
    private LocalDateTime dateEnd;

    @JsonView(Views.ScheduleView.class)
    private SubjectTeacherGroupDTO subjectTeacher;

    private ScheduleDayDTO scheduleDay;

    @JsonView(Views.ScheduleView.class)
    private LessonStatus status;
    public LessonDTO(){}

    public LessonDTO(Lesson lesson) {
        this.id = lesson.getId();
        this.dateStart = lesson.getDateStart();
        this.dateEnd = lesson.getDateEnd();
        this.subjectTeacher = new SubjectTeacherGroupDTO(lesson.getSubjectTeacher());
        this.status = lesson.getStatus();
    }

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

    public SubjectTeacherGroupDTO getSubjectTeacher() {
        return subjectTeacher;
    }

    public void setSubjectTeacher(SubjectTeacherGroupDTO subjectTeacher) {
        this.subjectTeacher = subjectTeacher;
    }

    public ScheduleDayDTO getScheduleDay() {
        return scheduleDay;
    }

    public void setScheduleDay(ScheduleDayDTO scheduleDay) {
        this.scheduleDay = scheduleDay;
    }

    public LessonStatus getStatus() {
        return status;
    }

    public void setStatus(LessonStatus status) {
        this.status = status;
    }


    @Override
    public int compareTo(LessonDTO o) {
        return Comparator.comparing(LessonDTO::getDateStart)
                .thenComparing(LessonDTO::getDateEnd)
                .compare(this, o);
    }
}
