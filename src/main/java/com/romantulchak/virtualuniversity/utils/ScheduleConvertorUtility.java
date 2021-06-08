package com.romantulchak.virtualuniversity.utils;

import com.romantulchak.virtualuniversity.dto.LessonDTO;
import com.romantulchak.virtualuniversity.dto.ScheduleDayDTO;
import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.ScheduleDay;

import java.util.Collection;
import java.util.stream.Collectors;

public final class ScheduleConvertorUtility {

    /***
     *
     * @param days Collection of ScheduleDay
     * @return Collection of ScheduleDayDTO
     * @exception RuntimeException
     */

    public static Collection<ScheduleDayDTO> convertScheduleDayToDTO(Collection<ScheduleDay> days){
        if (days == null){
            throw new RuntimeException("Days cannot be null");
        }
        return days.stream()
                .map(day -> new ScheduleDayDTO(day.getId(), day.getDay(), convertLessonsToDTO(day.getLessons())))
                .sorted()
                .collect(Collectors.toList());

    }

    public static Collection<LessonDTO> convertLessonsToDTO(Collection<Lesson> lessons) {
        if (lessons == null){
            throw new RuntimeException("Lessons cannot be empty");
        }
        return lessons.stream()
                .map(LessonDTO::new)
                .sorted()
                .collect(Collectors.toList());
    }
}
