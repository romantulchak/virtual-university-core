package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonView({Views.ScheduleView.class, Views.LessonStatusRequestView.class})
    private long id;
    @JsonView({Views.ScheduleView.class, Views.LessonStatusRequestView.class})
    private LocalDateTime dateStart;

    @JsonView({Views.ScheduleView.class, Views.LessonStatusRequestView.class})
    private LocalDateTime dateEnd;

    @JsonView({Views.ScheduleView.class, Views.LessonStatusRequestView.class})
    private SubjectTeacherGroupDTO subjectTeacher;

    private ScheduleDayDTO scheduleDay;

    @JsonView({Views.ScheduleView.class, Views.LessonStatusRequestView.class})
    private LessonStatus status;

    @JsonView({Views.ScheduleView.class, Views.LessonStatusRequestView.class})
    private String groupName;

    @JsonView({Views.ScheduleView.class, Views.LessonStatusRequestView.class})
    private String roomNumber;

    @JsonView(Views.LessonStatusRequestView.class)
    private LessonStatus previousStatus;

    @JsonView({Views.ScheduleView.class, Views.LessonStatusRequestView.class})
    private String comment;

    public LessonDTO(){}

    public LessonDTO(Lesson lesson) {
        this.id = lesson.getId();
        this.dateStart = lesson.getDateStart();
        this.dateEnd = lesson.getDateEnd();
        this.subjectTeacher = new SubjectTeacherGroupDTO(lesson.getSubjectTeacher());
        this.status = lesson.getStatus();
        this.roomNumber = lesson.getRoomNumber();
        this.previousStatus = lesson.getPreviousStatus();
        this.comment = lesson.getComment();
        if (lesson.getSubjectTeacher().getStudentGroup()!= null){
            this.groupName = lesson.getSubjectTeacher().getStudentGroup().getName();
        }
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int compareTo(LessonDTO o) {
        return Comparator.comparing(LessonDTO::getDateStart)
                .thenComparing(LessonDTO::getDateEnd)
                .compare(this, o);
    }
}
