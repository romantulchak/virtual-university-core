package com.romantulchak.virtualuniversity.contoller;

import com.romantulchak.virtualuniversity.model.StudentGroup;
import com.romantulchak.virtualuniversity.service.StudentGroupService;
import com.romantulchak.virtualuniversity.service.impl.StudentGroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/student-group")
public class StudentGroupController {

    StudentGroupServiceImpl studentGroupService;

    @Autowired
    public StudentGroupController(StudentGroupServiceImpl studentGroupService){
        this.studentGroupService = studentGroupService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public void create(@RequestBody StudentGroup studentGroup){
        studentGroupService.createGroup(studentGroup);
    }

}
