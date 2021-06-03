package com.romantulchak.virtualuniversity.model;

import com.romantulchak.virtualuniversity.model.enumes.LessonStatus;
import com.romantulchak.virtualuniversity.model.enumes.RequestDecision;

import javax.persistence.*;

@Entity
public class ScheduleLessonRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private LessonStatus actualStatus;

    @Enumerated(EnumType.STRING)
    private LessonStatus previousStatus;

    private String message;

    @ManyToOne
    private Lesson lesson;

    @Enumerated(EnumType.STRING)
    private RequestDecision decision;

    @Embedded
    private Request info;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LessonStatus getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(LessonStatus previousStatus) {
        this.previousStatus = previousStatus;
    }

    public LessonStatus getActualStatus() {
        return actualStatus;
    }

    public void setActualStatus(LessonStatus actualStatus) {
        this.actualStatus = actualStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public RequestDecision getDecision() {
        return decision;
    }

    public void setDecision(RequestDecision decision) {
        this.decision = decision;
    }

    public Request getInfo() {
        return info;
    }

    public void setInfo(Request request) {
        this.info = request;
    }
}
