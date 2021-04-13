package com.romantulchak.virtualuniversity.utils;

import com.romantulchak.virtualuniversity.dto.LessonDTO;
import com.romantulchak.virtualuniversity.dto.ScheduleDayDTO;
import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.ScheduleDay;

import java.util.Collection;
import java.util.stream.Collectors;

public final class ScheduleConvertorUtility {

    public static Collection<ScheduleDayDTO> convertScheduleDayToDTO(Collection<ScheduleDay> days) {
        return days.stream()
                .map(day -> new ScheduleDayDTO(day.getId(), day.getDay(), convertLessonsToDTO(day.getLessons())))
                .sorted()
                .collect(Collectors.toList());

    }

    private static Collection<LessonDTO> convertLessonsToDTO(Collection<Lesson> lessons) {
        return lessons.stream().map(LessonDTO::new).sorted().collect(Collectors.toList());
    }
}
