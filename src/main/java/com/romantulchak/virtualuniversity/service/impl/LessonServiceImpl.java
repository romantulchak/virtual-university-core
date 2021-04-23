package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.LessonDTO;
import com.romantulchak.virtualuniversity.exception.LessonAtThatTimeAlreadyExistsException;
import com.romantulchak.virtualuniversity.exception.LessonIsNullException;
import com.romantulchak.virtualuniversity.exception.LessonNotFoundException;
import com.romantulchak.virtualuniversity.exception.TimeNotCorrectException;
import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.enumes.LessonStatus;
import com.romantulchak.virtualuniversity.repository.LessonRepository;
import com.romantulchak.virtualuniversity.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository){
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    @Override
    public LessonDTO addLessonToDay(Lesson lesson) {
        if (lesson != null) {
            if (!lessonRepository.existsLessonByDateStartLessThanEqualAndDateEndGreaterThanEqual(lesson.getDateStart(), lesson.getDateEnd())) {
                if (!lesson.getDateEnd().isBefore(lesson.getDateStart())) {
                    lesson.setStatus(LessonStatus.ACTIVE);
                    Lesson lessonAfterSave = lessonRepository.save(lesson);
                    return new LessonDTO(lessonAfterSave);
                } else {
                    throw new TimeNotCorrectException();
                }
            } else {
                throw new LessonAtThatTimeAlreadyExistsException(lesson.getDateStart(), lesson.getDateEnd());
            }
        }
        throw new LessonIsNullException();
    }

    @Override
    public LessonDTO updateLesson(Lesson lesson) {
        if (lessonRepository.existsById(lesson.getId())){
            return new LessonDTO(lessonRepository.save(lesson));
        }
        throw new LessonNotFoundException(lesson.getId());
    }

    @Override
    public void removeLessonsFromDay(long lessonId) {
        if (lessonRepository.existsById(lessonId))
            lessonRepository.deleteById(lessonId);
        else
            throw new LessonNotFoundException(lessonId);
    }
}
