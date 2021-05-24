package com.romantulchak.virtualuniversity.components;

import com.romantulchak.virtualuniversity.repository.LessonRepository;
import org.springframework.stereotype.Component;

@Component
public class AccessToLesson {
    private final LessonRepository lessonRepository;
    public AccessToLesson(LessonRepository lessonRepository){
        this.lessonRepository = lessonRepository;
    }
    public boolean checkAccess(long lessonId, long teacherId){
       return lessonRepository.existsByIdAndSubjectTeacher_Teacher_Id(lessonId, teacherId);
    }

}
