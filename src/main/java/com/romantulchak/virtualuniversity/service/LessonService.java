package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.LessonDTO;
import com.romantulchak.virtualuniversity.dto.ScheduleLessonRequestDTO;
import com.romantulchak.virtualuniversity.dto.pageable.PageableDTO;
import com.romantulchak.virtualuniversity.exception.LessonAtThatTimeAlreadyExistsException;
import com.romantulchak.virtualuniversity.exception.LessonStatusNotChangedException;
import com.romantulchak.virtualuniversity.exception.TimeNotCorrectException;
import com.romantulchak.virtualuniversity.payload.request.lesson.AddLessonToDayRequest;
import com.romantulchak.virtualuniversity.payload.request.ChangeDecisionRequest;
import com.romantulchak.virtualuniversity.payload.request.ChangeLessonStatus;
import com.romantulchak.virtualuniversity.payload.request.ChangeStatusRequest;
import com.romantulchak.virtualuniversity.payload.request.lesson.EditLessonRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface LessonService {

    /**
     * Adds lesson to selected day if range for lesson is incorrect
     * {@link LessonAtThatTimeAlreadyExistsException} will be thrown
     * if lesson end date is before lesson start date {@link TimeNotCorrectException}
     * will be thrown
     *
     * @param addLessonToDayRequest contains information about new lesson
     * @return {@link LessonDTO}
     */
    LessonDTO addLessonToDay(AddLessonToDayRequest addLessonToDayRequest);

    /**
     * Checks if lesson exists then delete it
     * otherwise throw {@link com.romantulchak.virtualuniversity.exception.LessonNotFoundException}
     *
     * @param lessonId lesson with id that will be deleted
     */
    void removeLessonsFromDay(long lessonId);

    /**
     * Checks if lesson exsists by id if not
     * throw {@link com.romantulchak.virtualuniversity.exception.LessonNotFoundException}
     * otherwise updates lesson fields
     *
     * @param editLessonRequest contains updated fields
     * @return {@link LessonDTO} with updated fields
     */
    LessonDTO updateLesson(EditLessonRequest editLessonRequest);

    /**
     * Checks if current lesson status isn't equal to
     * previous lesson status if not then change status for lesson
     * to current otherwise throw {@link LessonStatusNotChangedException}
     *
     * @param changeLessonStatus to get information about lesson and status
     */
    void changeLessonStatus(ChangeLessonStatus changeLessonStatus);

    PageableDTO<List<ScheduleLessonRequestDTO>> findLessonRequests(int page);

    void setRequestDecision(ChangeDecisionRequest requestDecision);

    void setRequestStatus(ChangeStatusRequest request, Authentication authentication);

}
