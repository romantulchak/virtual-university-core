package com.romantulchak.virtualuniversity.payload.request;

import com.romantulchak.virtualuniversity.model.enumes.LessonStatus;
import lombok.Data;

@Data
public class ChangeLessonStatus {

    private long lessonId;

    private long semesterId;

    private long teacherId;

    private LessonStatus status;

    private String message;
}
