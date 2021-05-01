package com.romantulchak.virtualuniversity.contoller;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.dto.ScheduleDTO;
import com.romantulchak.virtualuniversity.model.Schedule;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.service.impl.ScheduleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    @GetMapping(value = "/exportPdf-f/{scheduleId}", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public byte[] exportScheduleFullAsPDF(@PathVariable("scheduleId") long scheduleId){
        return scheduleService.exportFullScheduleAsPDF(scheduleId);
    }
    @GetMapping(value = "/exportPdf-w/{scheduleId}", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public byte[] exportScheduleWeekAsPDF(@PathVariable("scheduleId") long scheduleId){
        return scheduleService.exportScheduleForWeekPDF(scheduleId);
    }

}
