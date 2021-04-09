package com.romantulchak.virtualuniversity.contoller;

import com.romantulchak.virtualuniversity.model.Schedule;
import com.romantulchak.virtualuniversity.service.impl.ScheduleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/schedule")
public class ScheduleController {

    private ScheduleServiceImpl scheduleService;

    @Autowired
    public ScheduleController(ScheduleServiceImpl scheduleService){
        this.scheduleService = scheduleService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public void create(@RequestBody Schedule schedule){
        scheduleService.create(schedule);
    }

}
