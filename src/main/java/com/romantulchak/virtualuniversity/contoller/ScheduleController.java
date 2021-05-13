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

    @GetMapping("/findForGroup/{semesterId}")
    @JsonView(Views.ScheduleView.class)
    public ScheduleDTO findScheduleForGroup(@PathVariable("semesterId") long semesterId){
        return scheduleService.findScheduleForGroup(semesterId);
    }
    @GetMapping("/findScheduleIdForGroup/{semesterId}")
    public long findScheduleIdForGroup(@PathVariable("semesterId") long semesterId){
        return scheduleService.findScheduleIdForGroup(semesterId);
    }
    @GetMapping("/findScheduleForTeacherByGroup")
    public ScheduleDTO findScheduleForTeacherByGroup(@RequestParam(value = "teacherId") long teacherId, @RequestParam("groupId") long groupId, @RequestParam(value = "semesterId") long semesterId){
        return scheduleService.findScheduleForTeacherBeGroup(teacherId, groupId, semesterId);
    }
    @GetMapping(value = "/exportPdf-f/{scheduleId}", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public byte[] exportScheduleFullAsPDF(@PathVariable("scheduleId") long scheduleId){
        return scheduleService.exportFullScheduleAsPDF(scheduleId);
    }
    @GetMapping(value = "/exportPdf-w/{scheduleId}/{semesterId}", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public byte[] exportScheduleWeekAsPDF(@PathVariable("scheduleId") long scheduleId, @PathVariable("semesterId") long semesterId){
        return scheduleService.exportScheduleForWeekPDF(scheduleId, semesterId);
    }

}
