package com.romantulchak.virtualuniversity.contoller;

import com.fasterxml.jackson.annotation.JsonValue;
import com.romantulchak.virtualuniversity.service.impl.ScheduleDayServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/schedule-day")
public class ScheduleDayController {

    private final ScheduleDayServiceImpl scheduleDayService;

    public ScheduleDayController(ScheduleDayServiceImpl scheduleDayService){
        this.scheduleDayService = scheduleDayService;
    }

    @GetMapping("/checkIfFree")
    public boolean checkIfDayAvailable(@RequestParam(value = "scheduleId") long scheduleId, @RequestParam(value = "day")String day){
        return scheduleDayService.checkIfDayAvailable(scheduleId, day);
    }

}
