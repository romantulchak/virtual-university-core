package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.LessonDTO;
import com.romantulchak.virtualuniversity.dto.ScheduleLessonRequestDTO;
import com.romantulchak.virtualuniversity.dto.pageable.PageableDTO;
import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.ScheduleLessonRequest;
import com.romantulchak.virtualuniversity.model.enumes.RequestDecision;
import com.romantulchak.virtualuniversity.model.enumes.RequestStatus;
import com.romantulchak.virtualuniversity.payload.request.ChangeDecisionRequest;
import com.romantulchak.virtualuniversity.payload.request.ChangeStatusRequest;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.List;

public interface LessonService {

    LessonDTO addLessonToDay(Lesson lesson);
    void removeLessonsFromDay(long lessonId);
    LessonDTO updateLesson(Lesson lesson);
    void changeLessonStatus(ScheduleLessonRequest scheduleLessonRequest);
    PageableDTO<List<ScheduleLessonRequestDTO>> findLessonRequests(int page);
    void setRequestDecision(ChangeDecisionRequest requestDecision);
    void setRequestStatus(ChangeStatusRequest request, Authentication authentication);

}
