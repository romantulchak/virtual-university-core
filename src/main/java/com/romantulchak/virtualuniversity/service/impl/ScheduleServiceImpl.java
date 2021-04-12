package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.LessonDTO;
import com.romantulchak.virtualuniversity.dto.ScheduleDTO;
import com.romantulchak.virtualuniversity.dto.ScheduleDayDTO;
import com.romantulchak.virtualuniversity.dto.StudentGroupDTO;
import com.romantulchak.virtualuniversity.exception.ScheduleIsNullException;
import com.romantulchak.virtualuniversity.exception.ScheduleNotFoundException;
import com.romantulchak.virtualuniversity.model.Lesson;
import com.romantulchak.virtualuniversity.model.Schedule;
import com.romantulchak.virtualuniversity.model.ScheduleDay;
import com.romantulchak.virtualuniversity.model.StudentGroup;
import com.romantulchak.virtualuniversity.repository.LessonRepository;
import com.romantulchak.virtualuniversity.repository.ScheduleDayRepository;
import com.romantulchak.virtualuniversity.repository.ScheduleRepository;
import com.romantulchak.virtualuniversity.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleDayRepository scheduleDayRepository;
    private final LessonRepository lessonRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ScheduleDayRepository scheduleDayRepository, LessonRepository lessonRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleDayRepository = scheduleDayRepository;
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    @Override
    public void create(Schedule schedule) {
        if (schedule != null) {
            scheduleRepository.save(schedule);
            if (schedule.getDays() != null && schedule.getDays().size() > 0) {
                saveDays(schedule);
            }
        } else {
            throw new ScheduleIsNullException();
        }

    }

    //TODO: fix it
    @Override
    public ScheduleDTO findScheduleForGroup(long groupId) {
        long scheduleId = scheduleRepository.getScheduleId(groupId).orElseThrow(ScheduleNotFoundException::new);
        return scheduleRepository.findScheduleByGroupId(groupId)
                .map(schedule -> new ScheduleDTO(schedule.getId(), convertScheduleDayToDTO(schedule.getDays()), getStudentGroupDTO(schedule.getStudentGroup())))
                .orElse(new ScheduleDTO(scheduleId));



    }

    private StudentGroupDTO getStudentGroupDTO(StudentGroup studentGroup) {
        return new StudentGroupDTO.Builder(studentGroup.getId(), studentGroup.getName()).build();
    }

    private Collection<ScheduleDayDTO> convertScheduleDayToDTO(Collection<ScheduleDay> days) {
        return days.stream()
                .map(day -> new ScheduleDayDTO(day.getId(), day.getDay(), convertLessonsToDTO(day.getLessons())))
                .collect(Collectors.toList());

    }

    private Collection<LessonDTO> convertLessonsToDTO(Collection<Lesson> lessons) {
        return lessons.stream().map(LessonDTO::new).sorted().collect(Collectors.toList());
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
