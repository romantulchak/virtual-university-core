package com.romantulchak.virtualuniversity.contoller;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.dto.ScheduleDTO;
import com.romantulchak.virtualuniversity.model.Schedule;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.service.impl.ScheduleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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

    @GetMapping("/findForGroup/{groupId}")
    @JsonView(Views.ScheduleView.class)
    public ScheduleDTO findScheduleForGroup(@PathVariable("groupId") long groupId){
        return scheduleService.findScheduleForGroup(groupId);
    }
    @GetMapping("/findScheduleIdForGroup/{groupId}")
    public long findScheduleIdForGroup(@PathVariable("groupId") long groupId){
        return scheduleService.findScheduleIdForGroup(groupId);
    }
    @GetMapping("/findScheduleForTeacherByGroup")
    public ScheduleDTO findScheduleForTeacherByGroup(@RequestParam(value = "teacherId") long teacherId, @RequestParam("groupId") long groupId){
        return scheduleService.findScheduleForTeacherBeGroup(teacherId, groupId);
    }
    @GetMapping("/exportPdf/{scheduleId}")
    @ResponseBody
    public ResponseEntity<Resource> exportScheduleAsPDF(@PathVariable("scheduleId") long scheduleId){
        return scheduleService.exportScheduleAsPDF(scheduleId);
    }

}
