package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.repository.ScheduleDayRepository;
import com.romantulchak.virtualuniversity.service.ScheduleDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ScheduleDayServiceImpl implements ScheduleDayService {
    private ScheduleDayRepository scheduleDayRepository;

    @Autowired
    public ScheduleDayServiceImpl(ScheduleDayRepository scheduleDayRepository){
        this.scheduleDayRepository = scheduleDayRepository;
    }

    @Override
    public boolean checkIfDayAvailable(long scheduleId, String day) {
        LocalDate date = LocalDate.parse(day);
        return scheduleDayRepository.checkIfDayAvailable(scheduleId, date);
    }
}
