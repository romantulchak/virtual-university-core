package com.romantulchak.virtualuniversity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.romantulchak.virtualuniversity.dto.LessonDTO;
import com.romantulchak.virtualuniversity.model.enumes.LessonStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;

@Entity
public class Lesson implements Comparable<Lesson>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private LocalDateTime dateStart;

    @NotNull
    private LocalDateTime dateEnd;

    @ManyToOne
    private SubjectTeacherGroup subjectTeacher;

    @ManyToOne
    private ScheduleDay scheduleDay;

    @Enumerated(EnumType.STRING)
    private LessonStatus status;

    @Enumerated(EnumType.STRING)
    private LessonStatus previousStatus;

    private String roomNumber;

    private String comment;

    @OneToMany(mappedBy = "lesson", cascade = {CascadeType.REMOVE})
    private Collection<ScheduleLessonRequest> scheduleLessonRequests;

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

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public LessonStatus getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(LessonStatus previousStatus) {
        this.previousStatus = previousStatus;
    }

    public Collection<ScheduleLessonRequest> getScheduleLessonRequests() {
        return scheduleLessonRequests;
    }

    public void setScheduleLessonRequests(Collection<ScheduleLessonRequest> scheduleLessonRequests) {
        this.scheduleLessonRequests = scheduleLessonRequests;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int compareTo(Lesson o) {
        return Comparator.comparing(Lesson::getDateStart)
                .thenComparing(Lesson::getDateEnd)
                .compare(this, o);
    }
}
