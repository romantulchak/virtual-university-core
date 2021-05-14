package com.romantulchak.virtualuniversity.contoller;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.dto.SubjectTeacherGroupDTO;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.service.SubjectTeacherService;
import com.romantulchak.virtualuniversity.service.impl.SubjectTeacherServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/subject-teacher")
public class SubjectTeacherController {

    private final SubjectTeacherServiceImpl subjectTeacherService;

    public SubjectTeacherController(SubjectTeacherServiceImpl subjectTeacherService){
        this.subjectTeacherService = subjectTeacherService;
    }

    @GetMapping("/findSubjectsForGroupBySemester/{semesterId}/{groupId}")
    @JsonView(Views.StudentGroupView.class)
    public Collection<SubjectTeacherGroupDTO> findSubjectsForGroupBySemester(@PathVariable("semesterId") long semesterId, @PathVariable("groupId") long groupId){
        return subjectTeacherService.findSubjectsForGroupBySemester(semesterId, groupId);
    }

    @GetMapping("/findSubjectsForGroupByTeacherAndSemester")
    @JsonView(Views.StudentGroupView.class)
    public Collection<SubjectTeacherGroupDTO> findSubjectsForGroupByTeacherAndSemester(@RequestParam(value = "teacherId") long teacherId,
                                                                                       @RequestParam(value = "groupId") long groupId,
                                                                                       @RequestParam(value = "semesterId") long semesterId){
        return subjectTeacherService.getSubjectsTeacherGroup(groupId, teacherId, semesterId);
    }
}
