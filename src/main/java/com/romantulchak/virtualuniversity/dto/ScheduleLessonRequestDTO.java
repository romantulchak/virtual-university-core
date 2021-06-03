package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Request;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.model.enumes.LessonStatus;
import com.romantulchak.virtualuniversity.model.enumes.RequestDecision;


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
    private RequestDecision decision;

    @JsonView(Views.LessonStatusRequestView.class)
    private LessonStatus previousStatus;

    @JsonView(Views.LessonStatusRequestView.class)
    private Request info;

    public ScheduleLessonRequestDTO(long id, LessonStatus actualStatus, String message, LessonDTO lesson, RequestDecision decision, LessonStatus previousStatus, Request info) {
        this.id = id;
        this.actualStatus = actualStatus;
        this.message = message;
        this.lesson = lesson;
        this.decision = decision;
        this.previousStatus = previousStatus;
        this.info = info;
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

    public RequestDecision getDecision() {
        return decision;
    }

    public void setDecision(RequestDecision decision) {
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

    public Request getInfo() {
        return info;
    }

    public void setInfo(Request info) {
        this.info = info;
    }
}
