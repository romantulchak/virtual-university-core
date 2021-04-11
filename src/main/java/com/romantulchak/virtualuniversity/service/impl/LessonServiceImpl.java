package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.exception.LessonAtThatTimeAlreadyExistsException;
import com.romantulchak.virtualuniversity.exception.TimeNotCorrectException;
import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.enumes.LessonStatus;
import com.romantulchak.virtualuniversity.repository.LessonRepository;
import com.romantulchak.virtualuniversity.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository){
        this.lessonRepository = lessonRepository;
    }

    @Override
    public void addLessonToDay(Lesson lesson) {
        if (lesson != null) {
            lesson.setDateStart(lesson.getDateStart().plusHours(1));
            lesson.setDateEnd(lesson.getDateEnd().plusHours(1));
            if (!lessonRepository.existsLessonByDateStartLessThanEqualAndDateEndGreaterThanEqual(lesson.getDateStart(), lesson.getDateEnd())) {
                if (!lesson.getDateEnd().isBefore(lesson.getDateStart())) {
                    lesson.setStatus(LessonStatus.ACTIVE);
                    lessonRepository.save(lesson);
                } else {
                    throw new TimeNotCorrectException();
                }
            } else {
                throw new LessonAtThatTimeAlreadyExistsException(lesson.getDateStart(), lesson.getDateEnd());
            }
        }
    }
}
