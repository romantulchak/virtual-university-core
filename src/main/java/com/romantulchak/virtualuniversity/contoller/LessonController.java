package com.romantulchak.virtualuniversity.contoller;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.dto.LessonDTO;
import com.romantulchak.virtualuniversity.dto.ScheduleLessonRequestDTO;
import com.romantulchak.virtualuniversity.dto.pageable.PageableDTO;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.payload.request.lesson.AddLessonToDayRequest;
import com.romantulchak.virtualuniversity.payload.request.ChangeDecisionRequest;
import com.romantulchak.virtualuniversity.payload.request.ChangeLessonStatus;
import com.romantulchak.virtualuniversity.payload.request.ChangeStatusRequest;
import com.romantulchak.virtualuniversity.payload.request.lesson.EditLessonRequest;
import com.romantulchak.virtualuniversity.service.impl.LessonServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/lesson")
public class LessonController {

    private final LessonServiceImpl lessonService;

    public LessonController(LessonServiceImpl lessonService){
        this.lessonService = lessonService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public LessonDTO createLessonForDay(@Valid @RequestBody AddLessonToDayRequest addLessonToDayRequest){
       return lessonService.addLessonToDay(addLessonToDayRequest);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public void deleteLessonForDay(@PathVariable("id") long lessonId){
        lessonService.removeLessonsFromDay(lessonId);
    }

    @PutMapping("/update")
    @JsonView(Views.ScheduleView.class)
    public LessonDTO updateLesson(@Valid @RequestBody EditLessonRequest editLessonRequest){
       return lessonService.updateLesson(editLessonRequest);
    }

    @PostMapping("/change-status-request")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER') OR hasRole('TEACHER') AND @accessToLesson.checkAccess(#changeLessonStatus.lessonId, #changeLessonStatus.teacherId)")
    public void changeStatusRequest(@RequestBody ChangeLessonStatus changeLessonStatus){
        lessonService.changeLessonStatus(changeLessonStatus);
    }
    
    @GetMapping("/getLessonRequests")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public PageableDTO<List<ScheduleLessonRequestDTO>> findLessonRequests(@RequestParam(value = "page") int page){
        return lessonService.findLessonRequests(page);
    }

    @PutMapping("/changeRequestDecision")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public void changeRequestDecision(@RequestBody ChangeDecisionRequest changeDecisionRequest){
        lessonService.setRequestDecision(changeDecisionRequest);
    }

    @PutMapping("/changeRequestStatus")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public void changeRequestStatus(@RequestBody ChangeStatusRequest request, Authentication authentication){
        lessonService.setRequestStatus(request, authentication);
    }
}
