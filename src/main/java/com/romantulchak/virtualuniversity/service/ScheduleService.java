package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.ScheduleDTO;
import com.romantulchak.virtualuniversity.model.Schedule;

public interface ScheduleService {
    void create(Schedule schedule);

    ScheduleDTO findScheduleForGroup(long groupId);

}
