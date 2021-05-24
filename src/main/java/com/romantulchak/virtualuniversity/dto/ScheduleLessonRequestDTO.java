package com.romantulchak.virtualuniversity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.model.enumes.LessonStatus;


public class ScheduleLessonRequestDTO {

    @JsonView(Views.LessonStatusRequestView.class)
    private long id;

    @JsonView(Views.LessonStatusRequestView.class)
    private LessonStatus status;

    @JsonView(Views.LessonStatusRequestView.class)
    private String message;

    @JsonView(Views.LessonStatusRequestView.class)
    private LessonDTO lesson;

    public ScheduleLessonRequestDTO(long id, LessonStatus status, String message, LessonDTO lesson) {
        this.id = id;
        this.status = status;
        this.message = message;
        this.lesson = lesson;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LessonStatus getStatus() {
        return status;
    }

    public void setStatus(LessonStatus status) {
        this.status = status;
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
}
