package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.ScheduleDTO;
import com.romantulchak.virtualuniversity.model.Schedule;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface ScheduleService {
    void create(Schedule schedule);

    ScheduleDTO findScheduleForGroup(long groupId);

    long findScheduleIdForGroup(long groupId);

    ScheduleDTO findScheduleForTeacherBeGroup(long teacherId, long groupId);

    ResponseEntity<Resource> exportScheduleAsPDF(long scheduleId);
}
