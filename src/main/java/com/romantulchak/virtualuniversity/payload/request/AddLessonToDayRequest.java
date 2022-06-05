package com.romantulchak.virtualuniversity.payload.request;

import com.romantulchak.virtualuniversity.model.ScheduleDay;
import com.romantulchak.virtualuniversity.model.SubjectTeacherGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class AddLessonToDayRequest {

    @NotNull(message = "lesson.start.time.not.null")
    private LocalDateTime dateStart;

    @NotNull(message = "lesson.end.time.not.null")
    private LocalDateTime dateEnd;

    @NotBlank(message = "lesson.room.cannot.be.empty")
    private String roomNumber;

    private SubjectTeacherGroup subjectTeacher;

    private ScheduleDay scheduleDay;
}
