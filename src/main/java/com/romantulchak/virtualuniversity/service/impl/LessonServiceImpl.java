package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.LessonDTO;
import com.romantulchak.virtualuniversity.dto.ScheduleLessonRequestDTO;
import com.romantulchak.virtualuniversity.exception.*;
import com.romantulchak.virtualuniversity.model.*;
import com.romantulchak.virtualuniversity.model.enumes.RoleType;
import com.romantulchak.virtualuniversity.model.enumes.LessonStatus;
import com.romantulchak.virtualuniversity.model.enumes.RequestStatus;
import com.romantulchak.virtualuniversity.repository.*;
import com.romantulchak.virtualuniversity.service.LessonService;
import com.romantulchak.virtualuniversity.utils.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService{

    private final LessonRepository lessonRepository;
    private final ScheduleLessonRepository scheduleLessonRepository;
    private final TeacherRepository teacherRepository;
    private final NotificationServiceImpl notificationService;
    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository,
                             ScheduleLessonRepository scheduleLessonRepository,
                             TeacherRepository teacherRepository,
                             NotificationServiceImpl notificationService) {
        this.lessonRepository = lessonRepository;
        this.scheduleLessonRepository = scheduleLessonRepository;
        this.teacherRepository = teacherRepository;
        this.notificationService = notificationService;
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
        if (!scheduleLessonRequest.getActualStatus().equals(scheduleLessonRequest.getLesson().getStatus())) {
            lessonRepository.updateStatus(scheduleLessonRequest.getLesson().getId(), LessonStatus.PENDING, lessonStatus);
            scheduleLessonRequest.setPreviousStatus(lessonStatus);
            scheduleLessonRepository.save(scheduleLessonRequest);
            List<Teacher> teachers = teacherRepository.findTeachersByRole(RoleType.ROLE_ADMIN);
            notificationService.createAll(teachers, "New request added", Resource.NOTIFICATION_COUNTER_DESTINATION);
        } else {
            throw new LessonStatusNotChangedException();
        }
    }

    @Override
    public Collection<ScheduleLessonRequestDTO> findLessonRequests(int page) {
        Pageable pageable = PageRequest.of(page, 25);
        return scheduleLessonRepository.findAllRequests(pageable)
                .stream()
                .map(request -> new ScheduleLessonRequestDTO(request.getId(),
                        request.getActualStatus(),
                        request.getMessage(),
                        new LessonDTO(request.getLesson()),
                        request.getDecision(),
                        request.getPreviousStatus()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void setRequestDecision(long requestId, RequestStatus decision) {
        ScheduleLessonRequest request = scheduleLessonRepository.findById(requestId).orElseThrow(LessonRequestNotFound::new);
        scheduleLessonRepository.updateRequest(requestId, decision, request.getPreviousStatus());
        RequestUtility requestUtility = new RequestUtility(request);
        if (decision == RequestStatus.ACCEPTED) {
            updateRequest(request, requestUtility, request.getActualStatus(), Resource.MSG_REQUEST_ACCEPTED);
            notificationService.createAll(requestUtility.getStudents(), String.format(Resource.MSG_SCHEDULE_CHANGED, requestUtility.getDay()), Resource.NOTIFICATION_SCHEDULE_CHANGED);
        } else {
            updateRequest(request, requestUtility, request.getPreviousStatus(), Resource.MSG_REQUEST_REJECTED);
        }
    }

    private void updateRequest(ScheduleLessonRequest request, RequestUtility requestUtility, LessonStatus previousStatus, String msgRequestRejected) {
        lessonRepository.updateStatus(request.getLesson().getId(), previousStatus, request.getLesson().getPreviousStatus());
        notificationService.create(requestUtility.getTeacher(), String.format(msgRequestRejected, requestUtility.getDay(), requestUtility.getSubject()), requestUtility.getNotificationBox(), Resource.NOTIFICATION_SNAC_DESTINATION);
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
