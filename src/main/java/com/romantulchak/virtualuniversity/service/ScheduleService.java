package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.ScheduleDTO;
import com.romantulchak.virtualuniversity.model.Schedule;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface ScheduleService {
    void create(Schedule schedule);

    ScheduleDTO findScheduleForGroup(long semesterId);

    long findScheduleIdForGroup(long semesterId);

    ScheduleDTO findScheduleForTeacherByGroup(long teacherId, long groupId, long semesterId);

    byte[] exportFullScheduleAsPDF(long scheduleId, long semesterId);

    byte[] exportScheduleForWeekPDF(long scheduleId, long semesterId);
}
