package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.LessonDTO;
import com.romantulchak.virtualuniversity.model.Lesson;

public interface LessonService {

    LessonDTO addLessonToDay(Lesson lesson);
    void removeLessonsFromDay(long lessonId);

}
