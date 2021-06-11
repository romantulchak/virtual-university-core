package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.ScheduleDayDTO;
import com.romantulchak.virtualuniversity.exception.ScheduleDayNotFoundException;
import com.romantulchak.virtualuniversity.model.ScheduleDay;
import com.romantulchak.virtualuniversity.repository.LessonRepository;
import com.romantulchak.virtualuniversity.repository.ScheduleDayRepository;
import com.romantulchak.virtualuniversity.service.ScheduleDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

import static com.romantulchak.virtualuniversity.utils.DateParserUtility.parseStringToDate;
import static com.romantulchak.virtualuniversity.utils.ScheduleConvertorUtility.convertLessonsToDTO;
import static com.romantulchak.virtualuniversity.utils.ScheduleConvertorUtility.convertScheduleDayToDTO;

@Service
public class ScheduleDayServiceImpl implements ScheduleDayService {
    private final ScheduleDayRepository scheduleDayRepository;
    private final LessonRepository lessonRepository;
    @Autowired
    public ScheduleDayServiceImpl(ScheduleDayRepository scheduleDayRepository, LessonRepository lessonRepository){
        this.scheduleDayRepository = scheduleDayRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public boolean checkIfDayAvailable(long scheduleId, String day) {
        return scheduleDayRepository.checkIfDayAvailable(scheduleId, parseStringToDate(day));
    }

    @Override
    public Collection<ScheduleDayDTO> findAllDaysInRange(String dayAfter, String dayBefore, long scheduleId) {
        Collection<ScheduleDay> scheduleDaysByRange = scheduleDayRepository.findScheduleDaysByRange(parseStringToDate(dayAfter), parseStringToDate(dayBefore), scheduleId);
        return convertScheduleDayToDTO(scheduleDaysByRange);
    }

    @Override
    public Collection<ScheduleDayDTO> findDaysForWeek(long semesterId) {
        Collection<ScheduleDay> scheduleDaysForWeek = scheduleDayRepository.findScheduleDaysForWeek(semesterId, LocalDate.now(), LocalDate.now().plusWeeks(1));
        return convertScheduleDayToDTO(scheduleDaysForWeek);
    }

    @Override
    public Collection<ScheduleDayDTO> findDaysForTeacher(long teacherId, long semesterId) {
        Collection<ScheduleDay> days = scheduleDayRepository.findScheduleDayForTeacher(teacherId, semesterId);
        for (ScheduleDay day : days){
            day.setLessons(lessonRepository.findLessonsForTeacherByDay(day.getId(), teacherId));
        }
        return convertScheduleDayToDTO(days);
    }

    @Override
    public ScheduleDayDTO getDayLessons(String stringDate, String groupName, long semesterId) {
        LocalDate date = LocalDate.parse(stringDate);
        ScheduleDay day = scheduleDayRepository.findScheduleDayByAndGroupName(date, groupName, semesterId).orElseThrow(ScheduleDayNotFoundException::new);
        return new ScheduleDayDTO(day.getId(), day.getDay(), convertLessonsToDTO(day.getLessons()));
    }

    @Override
    public Collection<ScheduleDayDTO> findDaysForWeek(long teacherId, long semesterId) {
        Collection<ScheduleDay> days = scheduleDayRepository.findScheduleDayForTeacherForWeek(teacherId, semesterId, LocalDate.now(), LocalDate.now().plusWeeks(1));
        return convertScheduleDayToDTO(days);
    }

    @Override
    public void deleteDay(long dayId) {
        if (scheduleDayRepository.existsById(dayId))
            scheduleDayRepository.deleteById(dayId);
        else {
            throw new ScheduleDayNotFoundException(dayId);
        }
    }
}
