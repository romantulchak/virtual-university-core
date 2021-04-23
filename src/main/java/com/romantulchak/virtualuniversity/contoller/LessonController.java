package com.romantulchak.virtualuniversity.contoller;

import com.romantulchak.virtualuniversity.dto.LessonDTO;
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
}
