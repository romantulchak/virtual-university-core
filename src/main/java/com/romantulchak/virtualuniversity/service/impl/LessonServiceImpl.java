package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.LessonDTO;
import com.romantulchak.virtualuniversity.dto.ScheduleLessonRequestDTO;
import com.romantulchak.virtualuniversity.exception.*;
import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.ScheduleLessonRequest;
import com.romantulchak.virtualuniversity.model.enumes.LessonStatus;
import com.romantulchak.virtualuniversity.repository.LessonRepository;
import com.romantulchak.virtualuniversity.repository.ScheduleLessonRepository;
import com.romantulchak.virtualuniversity.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    private final ScheduleLessonRepository scheduleLessonRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, ScheduleLessonRepository scheduleLessonRepository) {
        this.lessonRepository = lessonRepository;
        this.scheduleLessonRepository = scheduleLessonRepository;
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
        if (lessonRepository.existsById(lesson.getId())) {
            return new LessonDTO(lessonRepository.save(lesson));
        }
        throw new LessonNotFoundException(lesson.getId());
    }

    @Transactional
    @Override
    public void changeLessonStatus(ScheduleLessonRequest scheduleLessonRequest) {
        LessonStatus lessonStatus = lessonRepository.findLessonActualStatus(scheduleLessonRequest.getLesson().getId())
                .orElseThrow(() -> new LessonNotFoundException(scheduleLessonRequest.getLesson().getId()));
        if (!scheduleLessonRequest.getStatus().equals(scheduleLessonRequest.getLesson().getStatus())) {
            lessonRepository.updateStatus(scheduleLessonRequest.getLesson().getId(), LessonStatus.PENDING, lessonStatus);
            scheduleLessonRepository.save(scheduleLessonRequest);
        } else {
            throw new LessonStatusNotChangedException();
        }
    }

    @Override
    public Collection<ScheduleLessonRequestDTO> findLessonRequests(int page) {
        Pageable pageable = PageRequest.of(page, 25);
        return scheduleLessonRepository.findAllRequests(pageable)
                .stream()
                .map(request-> new ScheduleLessonRequestDTO(request.getId(), request.getStatus(), request.getMessage(), new LessonDTO(request.getLesson())))
                .collect(Collectors.toList());
    }

    @Override
    public void removeLessonsFromDay(long lessonId) {
        if (lessonRepository.existsById(lessonId))
            lessonRepository.deleteById(lessonId);
        else {
            throw new LessonNotFoundException(lessonId);
        }
    }
}
