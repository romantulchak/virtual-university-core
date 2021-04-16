package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.ScheduleDayDTO;
import com.romantulchak.virtualuniversity.exception.ScheduleDayNotFoundException;
import com.romantulchak.virtualuniversity.exception.ScheduleNotFoundException;
import com.romantulchak.virtualuniversity.model.ScheduleDay;
import com.romantulchak.virtualuniversity.repository.ScheduleDayRepository;
import com.romantulchak.virtualuniversity.service.ScheduleDayService;
import com.romantulchak.virtualuniversity.utils.ScheduleConvertorUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

import static com.romantulchak.virtualuniversity.utils.DateParserUtility.parseStringToDate;
import static com.romantulchak.virtualuniversity.utils.ScheduleConvertorUtility.convertScheduleDayToDTO;

@Service
public class ScheduleDayServiceImpl implements ScheduleDayService {
    private final ScheduleDayRepository scheduleDayRepository;

    @Autowired
    public ScheduleDayServiceImpl(ScheduleDayRepository scheduleDayRepository){
        this.scheduleDayRepository = scheduleDayRepository;
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
    public Collection<ScheduleDayDTO> findDaysForWeek(long groupId) {
        Collection<ScheduleDay> scheduleDaysForWeek = scheduleDayRepository.findScheduleDaysForWeek(groupId, LocalDate.now(), LocalDate.now().plusWeeks(1));
        return convertScheduleDayToDTO(scheduleDaysForWeek);
    }

    @Override
    public void deleteDay(long dayId) {
        if (scheduleDayRepository.existsById(dayId))
            scheduleDayRepository.deleteById(dayId);
        else
            throw new ScheduleDayNotFoundException(dayId);

    }
}