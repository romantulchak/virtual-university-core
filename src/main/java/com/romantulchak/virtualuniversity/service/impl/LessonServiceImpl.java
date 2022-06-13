package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.components.Transformer;
import com.romantulchak.virtualuniversity.constant.AppConstants;
import com.romantulchak.virtualuniversity.constant.Resource;
import com.romantulchak.virtualuniversity.dto.LessonDTO;
import com.romantulchak.virtualuniversity.dto.ScheduleLessonRequestDTO;
import com.romantulchak.virtualuniversity.dto.pageable.PageableDTO;
import com.romantulchak.virtualuniversity.exception.*;
import com.romantulchak.virtualuniversity.model.*;
import com.romantulchak.virtualuniversity.model.enumes.LessonStatus;
import com.romantulchak.virtualuniversity.model.enumes.RequestDecision;
import com.romantulchak.virtualuniversity.model.enumes.RequestStatus;
import com.romantulchak.virtualuniversity.model.enumes.RoleType;
import com.romantulchak.virtualuniversity.payload.request.ChangeDecisionRequest;
import com.romantulchak.virtualuniversity.payload.request.ChangeLessonStatus;
import com.romantulchak.virtualuniversity.payload.request.ChangeStatusRequest;
import com.romantulchak.virtualuniversity.payload.request.lesson.AddLessonToDayRequest;
import com.romantulchak.virtualuniversity.payload.request.lesson.EditLessonRequest;
import com.romantulchak.virtualuniversity.payload.response.ChangeStatusResponse;
import com.romantulchak.virtualuniversity.repository.LessonRepository;
import com.romantulchak.virtualuniversity.repository.ScheduleLessonRequestRepository;
import com.romantulchak.virtualuniversity.repository.SubjectTeacherRepository;
import com.romantulchak.virtualuniversity.repository.TeacherRepository;
import com.romantulchak.virtualuniversity.service.LessonService;
import com.romantulchak.virtualuniversity.utils.RequestUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.romantulchak.virtualuniversity.constant.Resource.NOTIFICATION_CHANGE_REQUEST_STATUS;
import static com.romantulchak.virtualuniversity.utils.PageUtil.getFrontendPageNumber;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ScheduleLessonRequestRepository scheduleLessonRequestRepository;
    private final TeacherRepository teacherRepository;
    private final NotificationServiceImpl notificationService;
    private final MessageSource messageSource;
    private final SubjectTeacherRepository subjectTeacherRepository;
    private final Transformer transformer;


    /**
     * {@inheritDoc}
     */
    @Override
    public LessonDTO addLessonToDay(AddLessonToDayRequest addLessonToDayRequest) {
        if (!lessonRepository.existsLessonByDateStartLessThanEqualAndDateEndGreaterThanEqualAndScheduleDayId(addLessonToDayRequest.getDateStart(), addLessonToDayRequest.getDateEnd(), addLessonToDayRequest.getScheduleDay().getId())) {
            if (!addLessonToDayRequest.getDateEnd().isBefore(addLessonToDayRequest.getDateStart())) {
                Lesson lesson = new Lesson()
                        .setDateStart(addLessonToDayRequest.getDateStart())
                        .setDateEnd(addLessonToDayRequest.getDateEnd())
                        .setSubjectTeacher(addLessonToDayRequest.getSubjectTeacher())
                        .setScheduleDay(addLessonToDayRequest.getScheduleDay())
                        .setRoomNumber(addLessonToDayRequest.getRoomNumber())
                        .setStatus(LessonStatus.ACTIVE);
                Lesson lessonAfterSave = lessonRepository.save(lesson);
                return new LessonDTO(lessonAfterSave);
            } else {
                throw new TimeNotCorrectException(messageSource);
            }
        }
        throw new LessonAtThatTimeAlreadyExistsException(addLessonToDayRequest.getDateStart(), addLessonToDayRequest.getDateEnd(), messageSource);
    }

    @Override
    @Transactional
    public LessonDTO updateLesson(EditLessonRequest editLessonRequest) {
        if (!lessonRepository.existsLessonByDateStartLessThanEqualAndDateEndGreaterThanEqualAndScheduleDayIdAndIdNot(editLessonRequest.getDateStart(), editLessonRequest.getDateEnd(), editLessonRequest.getDayId(), editLessonRequest.getLessonId())) {
            SubjectTeacherGroup subjectTeacherGroup = subjectTeacherRepository.findById(editLessonRequest.getSubjectTeacherId())
                    .orElseThrow(() -> new SubjectTeacherGroupNotFoundException(messageSource));
            Lesson lesson = lessonRepository.findById(editLessonRequest.getLessonId())
                    .orElseThrow(() -> new LessonNotFoundException(editLessonRequest.getLessonId(), messageSource))
                    .setDateStart(editLessonRequest.getDateStart())
                    .setDateEnd(editLessonRequest.getDateEnd())
                    .setRoomNumber(editLessonRequest.getRoomNumber())
                    .setSubjectTeacher(subjectTeacherGroup);
            Lesson lessonAfterSave = lessonRepository.save(lesson);
            return transformer.convertToLessonDTO(lessonAfterSave);
        } else {
            throw new LessonAtThatTimeAlreadyExistsException(editLessonRequest.getDateStart(), editLessonRequest.getDateEnd(), messageSource);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void changeLessonStatus(ChangeLessonStatus changeLessonStatus) {
        LessonStatus lessonStatus = lessonRepository.findLessonActualStatus(changeLessonStatus.getLessonId())
                .orElseThrow(() -> new LessonNotFoundException(changeLessonStatus.getLessonId(), messageSource));
        if (changeLessonStatus.getStatus() != lessonStatus) {
            lessonRepository.updateStatus(changeLessonStatus.getLessonId(), LessonStatus.PENDING, lessonStatus, null);
            Request request = new Request(RequestStatus.CHECK_OUT);
            ScheduleLessonRequest scheduleLessonRequest = new ScheduleLessonRequest()
                    .setPreviousStatus(lessonStatus)
                    .setActualStatus(changeLessonStatus.getStatus())
                    .setInfo(request)
                    .setMessage(changeLessonStatus.getMessage())
                    .setLesson(new Lesson(changeLessonStatus.getLessonId()))
                    .setSemesterId(changeLessonStatus.getSemesterId());
            scheduleLessonRequestRepository.save(scheduleLessonRequest);
            List<Teacher> teachers = teacherRepository.findTeachersByRole(RoleType.ROLE_ADMIN);
            notificationService.createAll(teachers, AppConstants.NEW_REQUEST_ADDED_NOTIFICATION, Resource.NOTIFICATION_COUNTER_DESTINATION);
        } else {
            throw new LessonStatusNotChangedException();
        }
    }

    @Override
    public PageableDTO<List<ScheduleLessonRequestDTO>> findLessonRequests(int currentPage) {
        Pageable pageable = PageRequest.of(getFrontendPageNumber(currentPage), 3);
        Page<ScheduleLessonRequest> page = scheduleLessonRequestRepository.findAllRequests(pageable);
        List<ScheduleLessonRequestDTO> requests = page.getContent().stream()
                .map(request -> new ScheduleLessonRequestDTO(request.getId(),
                        request.getActualStatus(),
                        request.getMessage(),
                        new LessonDTO(request.getLesson()),
                        request.getDecision(),
                        request.getPreviousStatus(),
                        request.getInfo(),
                        request.getSemesterId())).collect(Collectors.toList());
        return new PageableDTO<>(currentPage, page.getTotalPages(), requests, page.getTotalElements());
    }

    @Transactional
    @Override
    public void setRequestDecision(ChangeDecisionRequest requestDecision) {
        ScheduleLessonRequest request = scheduleLessonRequestRepository.findById(requestDecision.getId()).orElseThrow(LessonRequestNotFound::new);
        scheduleLessonRequestRepository.updateRequest(requestDecision.getId(), requestDecision.getRequestDecision(), request.getPreviousStatus());
        RequestUtility requestUtility = new RequestUtility(request);
        if (requestDecision.getRequestDecision() == RequestDecision.ACCEPTED) {
            updateRequest(request, requestUtility, request.getActualStatus(), Resource.MSG_REQUEST_ACCEPTED, requestDecision.getComment());
            notificationService.createAll(requestUtility.getStudents(), String.format(Resource.MSG_SCHEDULE_CHANGED, requestUtility.getDay()), Resource.NOTIFICATION_SCHEDULE_CHANGED);
        } else {
            updateRequest(request, requestUtility, request.getPreviousStatus(), Resource.MSG_REQUEST_REJECTED, requestDecision.getComment());
        }
    }

    @Transactional
    @Override
    public void setRequestStatus(ChangeStatusRequest changeStatusRequest, Authentication authentication) {
        ScheduleLessonRequest scheduleLessonRequest = scheduleLessonRequestRepository.findById(changeStatusRequest.getId()).orElseThrow(() -> new LessonNotFoundException(messageSource));
        if (scheduleLessonRequest.getInfo().getRequestStatus() != null && !scheduleLessonRequest.getInfo().getRequestStatus().equals(changeStatusRequest.getRequestStatus())) {
            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
            scheduleLessonRequestRepository.setRequestStatus(changeStatusRequest.getId(), changeStatusRequest.getRequestStatus(), user.getFullName(), user.getUsername());
            ChangeStatusResponse response = new ChangeStatusResponse(changeStatusRequest.getId(), changeStatusRequest.getRequestStatus(), user.getFullName(), user.getUsername());
            notificationService.send(response, NOTIFICATION_CHANGE_REQUEST_STATUS);
        }
    }

    private void updateRequest(ScheduleLessonRequest request, RequestUtility requestUtility, LessonStatus previousStatus, String msgRequestRejected, String comment) {
        lessonRepository.updateStatus(request.getLesson().getId(), previousStatus, request.getLesson().getPreviousStatus(), comment);
        scheduleLessonRequestRepository.setRequestStatus(request.getId(), RequestStatus.CHECK_OUT);
        notificationService.create(requestUtility.getTeacher(), String.format(msgRequestRejected, requestUtility.getDay(), requestUtility.getSubject()), requestUtility.getNotificationBox(), Resource.NOTIFICATION_SNAC_DESTINATION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeLessonsFromDay(long lessonId) {
        if (lessonRepository.existsById(lessonId))
            lessonRepository.deleteById(lessonId);
        else {
            throw new LessonNotFoundException(lessonId, messageSource);
        }
    }
}
