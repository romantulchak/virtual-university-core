package com.romantulchak.virtualuniversity.contoller;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.dto.ScheduleDayDTO;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.service.impl.ScheduleDayServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;

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
    @GetMapping("/findScheduleInRange")
    @JsonView(Views.ScheduleView.class)
    public Collection<ScheduleDayDTO> findScheduleInRange(@RequestParam(value = "scheduleId") long scheduleId, @RequestParam(value = "dayAfter")String dayAfter, @RequestParam(value = "dayBefore") String dayBefore){
        return scheduleDayService.findAllDaysInRange(dayAfter, dayBefore, scheduleId);
    }

    @GetMapping("/findScheduleForTwoWeek/{groupId}/{semesterId}")
    @JsonView(Views.ScheduleView.class)
    public Collection<ScheduleDayDTO> findDaysForWeek(@PathVariable("groupId") long groupId, @PathVariable("semesterId") long semesterId){
        return scheduleDayService.findDaysForWeek(semesterId);
    }

    @GetMapping("/findScheduleForTeacher")
    @JsonView(Views.ScheduleView.class)
    public Collection<ScheduleDayDTO> findDaysForTeacherByGroup(@RequestParam(value = "teacherId") long teacherId, @RequestParam(value = "semesterId") long semesterId){
        return scheduleDayService.findDaysForTeacherByGroup(teacherId, semesterId);
    }

    @DeleteMapping("/delete/{dayId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteDay(@PathVariable("dayId") long dayId){
        scheduleDayService.deleteDay(dayId);
    }
}
