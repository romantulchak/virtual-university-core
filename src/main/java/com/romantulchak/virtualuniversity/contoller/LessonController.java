package com.romantulchak.virtualuniversity.contoller;

import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.service.impl.LessonServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public void createLessonForDay(@RequestBody Lesson lesson){
        lessonService.addLessonToDay(lesson);
    }



}
