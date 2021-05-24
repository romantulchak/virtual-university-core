package com.romantulchak.virtualuniversity.model;

import com.romantulchak.virtualuniversity.model.enumes.LessonStatus;

import javax.persistence.*;

@Entity
public class ScheduleLessonRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private LessonStatus status;

    private String message;

    @OneToOne
    private Lesson lesson;


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

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}
