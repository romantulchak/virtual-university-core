package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.exception.ScheduleIsNullException;
import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.Schedule;
import com.romantulchak.virtualuniversity.model.ScheduleDay;
import com.romantulchak.virtualuniversity.repository.LessonRepository;
import com.romantulchak.virtualuniversity.repository.ScheduleDayRepository;
import com.romantulchak.virtualuniversity.repository.ScheduleRepository;
import com.romantulchak.virtualuniversity.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleDayRepository scheduleDayRepository;
    private final LessonRepository lessonRepository;
    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ScheduleDayRepository scheduleDayRepository, LessonRepository lessonRepository){
        this.scheduleRepository = scheduleRepository;
        this.scheduleDayRepository = scheduleDayRepository;
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    @Override
    public void create(Schedule schedule) {
        if(schedule != null){
            scheduleRepository.save(schedule);
            saveDays(schedule);
        }else {
            throw new ScheduleIsNullException();
        }

    }

    private void saveDays(Schedule schedule) {
        for (ScheduleDay day : schedule.getDays()) {
            day.setSchedule(schedule);
            scheduleDayRepository.save(day);
            saveLessons(day);
        }
    }

    private void saveLessons(ScheduleDay day) {
        for (Lesson lesson : day.getLessons()) {
            lesson.setScheduleDay(day);
            lessonRepository.save(lesson);
        }
    }
}
