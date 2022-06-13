package com.romantulchak.virtualuniversity.payload.request.lesson;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class EditLessonRequest {

    @NotNull(message = "lesson.start.time.not.null")
    private LocalDateTime dateStart;

    @NotNull(message = "lesson.end.time.not.null")
    private LocalDateTime dateEnd;

    @NotNull(message = "lesson.room.cannot.be.empty")
    private String roomNumber;

    private long dayId;

    private long subjectTeacherId;

    private long lessonId;
}
