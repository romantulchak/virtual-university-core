package com.romantulchak.virtualuniversity.contoller;

import com.romantulchak.virtualuniversity.dto.LessonDTO;
import com.romantulchak.virtualuniversity.dto.ScheduleLessonRequestDTO;
import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.ScheduleLessonRequest;
import com.romantulchak.virtualuniversity.model.enumes.RequestDecision;
import com.romantulchak.virtualuniversity.payload.request.ChangeStatusRequest;
import com.romantulchak.virtualuniversity.service.impl.LessonServiceImpl;
import com.romantulchak.virtualuniversity.service.impl.UserDetailsImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
    public LessonDTO createLessonForDay(@RequestBody Lesson lesson){
       return lessonService.addLessonToDay(lesson);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public void deleteLessonForDay(@PathVariable("id") long lessonId){
        lessonService.removeLessonsFromDay(lessonId);
    }

    @PutMapping("/update")
    public LessonDTO updateLesson(@RequestBody Lesson lesson){
        return lessonService.updateLesson(lesson);
    }

    @PostMapping("/change-status-request/{lessonId}/{teacherId}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER') OR hasRole('TEACHER') AND @accessToLesson.checkAccess(#lessonId, #teacherId)")
    public void changeStatusRequest(@PathVariable("lessonId") long lessonId, @PathVariable("teacherId") long teacherId, @RequestBody ScheduleLessonRequest scheduleLessonRequest){
        lessonService.changeLessonStatus(scheduleLessonRequest);
    }
    
    @GetMapping("/getLessonRequests")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public Collection<ScheduleLessonRequestDTO> findLessonRequests(@RequestParam(value = "page") int page){
        return lessonService.findLessonRequests(page);
    }

    @PutMapping("/changeRequestDecision")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public void changeRequestDecision(@RequestParam(value = "requestId") long requestId, @RequestParam(value = "decision") RequestDecision decision){
        lessonService.setRequestDecision(requestId, decision);
    }

    @PutMapping("/changeRequestStatus")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public void changeRequestStatus(@RequestBody ChangeStatusRequest request, Authentication authentication){
        lessonService.setRequestStatus(request, authentication);
    }
}
