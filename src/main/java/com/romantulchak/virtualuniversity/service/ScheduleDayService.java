package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.ScheduleDayDTO;

import java.util.Collection;

public interface ScheduleDayService {
    boolean checkIfDayAvailable(long scheduleId, String day);
    Collection<ScheduleDayDTO> findAllDaysInRange(String dayAfter, String dayBefore, long scheduleId);
    void deleteDay(long dayId);
    Collection<ScheduleDayDTO> findDaysForWeek(long semesterId);
    Collection<ScheduleDayDTO> findDaysForWeek(long teacherId, long semesterId);
    Collection<ScheduleDayDTO> findDaysForTeacher(long teacherId, long semesterId);
    ScheduleDayDTO getDayLessons(String day, String groupName, long semesterId);
}
