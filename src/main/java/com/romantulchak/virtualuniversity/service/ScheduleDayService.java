package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.ScheduleDayDTO;

import java.time.LocalDate;
import java.util.Collection;

public interface ScheduleDayService {
    boolean checkIfDayAvailable(long scheduleId, String day);
    Collection<ScheduleDayDTO> findAllDaysInRange(String dayAfter, String dayBefore, long scheduleId);
}
