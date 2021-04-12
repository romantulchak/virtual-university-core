package com.romantulchak.virtualuniversity.service;

import java.time.LocalDate;

public interface ScheduleDayService {
    boolean checkIfDayAvailable(long scheduleId, String day);
}
