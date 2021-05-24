package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.LessonDTO;
import com.romantulchak.virtualuniversity.dto.ScheduleLessonRequestDTO;
import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.ScheduleLessonRequest;

import java.util.Collection;

public interface LessonService {

    LessonDTO addLessonToDay(Lesson lesson);
    void removeLessonsFromDay(long lessonId);
    LessonDTO updateLesson(Lesson lesson);
    void changeLessonStatus(ScheduleLessonRequest scheduleLessonRequest);
    Collection<ScheduleLessonRequestDTO> findLessonRequests(int page);
}
