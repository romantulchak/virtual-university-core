package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.model.enumes.LessonStatus;
import com.romantulchak.virtualuniversity.model.enumes.RequestStatus;

import javax.persistence.Column;


public class ScheduleLessonRequestDTO {

    @JsonView(Views.LessonStatusRequestView.class)
    private long id;

    @JsonView(Views.LessonStatusRequestView.class)
    private LessonStatus actualStatus;

    @JsonView(Views.LessonStatusRequestView.class)
    private String message;

    @JsonView(Views.LessonStatusRequestView.class)
    private LessonDTO lesson;

    @JsonView(Views.LessonStatusRequestView.class)
    private RequestStatus decision;

    @JsonView(Views.LessonStatusRequestView.class)
    private LessonStatus previousStatus;



    public ScheduleLessonRequestDTO(long id, LessonStatus actualStatus, String message, LessonDTO lesson, RequestStatus decision, LessonStatus previousStatus) {
        this.id = id;
        this.actualStatus = actualStatus;
        this.message = message;
        this.lesson = lesson;
        this.decision = decision;
        this.previousStatus = previousStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LessonDTO getLesson() {
        return lesson;
    }

    public void setLesson(LessonDTO lesson) {
        this.lesson = lesson;
    }

    public RequestStatus getDecision() {
        return decision;
    }

    public void setDecision(RequestStatus decision) {
        this.decision = decision;
    }

    public LessonStatus getActualStatus() {
        return actualStatus;
    }

    public void setActualStatus(LessonStatus actualStatus) {
        this.actualStatus = actualStatus;
    }

    public LessonStatus getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(LessonStatus previousStatus) {
        this.previousStatus = previousStatus;
    }
}
