package com.romantulchak.virtualuniversity.components;

import com.romantulchak.virtualuniversity.dto.CourseDTO;
import com.romantulchak.virtualuniversity.dto.LessonDTO;
import com.romantulchak.virtualuniversity.dto.ScheduleLessonRequestDTO;
import com.romantulchak.virtualuniversity.model.Course;
import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.ScheduleLessonRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Transformer {

    private final ModelMapper modelMapper;

    public CourseDTO convertToCourseDTO(Course course) {
        return modelMapper.map(course, CourseDTO.class);
    }

    public ScheduleLessonRequestDTO convertToScheduleLessonRequestDTO(ScheduleLessonRequest scheduleLessonRequest) {
        return modelMapper.map(scheduleLessonRequest, ScheduleLessonRequestDTO.class);
    }

    public LessonDTO convertToLessonDTO(Lesson lesson) {
        return modelMapper.map(lesson, LessonDTO.class);
    }
}
