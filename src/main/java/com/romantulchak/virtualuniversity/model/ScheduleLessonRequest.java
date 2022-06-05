package com.romantulchak.virtualuniversity.model;

import com.romantulchak.virtualuniversity.model.enumes.LessonStatus;
import com.romantulchak.virtualuniversity.model.enumes.RequestDecision;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Accessors(chain = true)
@Getter
@Setter
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

    private long semesterId;
}
