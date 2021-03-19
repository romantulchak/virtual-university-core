package com.romantulchak.virtualuniversity.contoller;

import com.romantulchak.virtualuniversity.service.StudentGroupService;
import com.romantulchak.virtualuniversity.service.impl.StudentGroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/student-group")
public class StudentGroupController {

    StudentGroupServiceImpl studentGroupService;

    @Autowired
    public StudentGroupController(StudentGroupServiceImpl studentGroupService){
        this.studentGroupService = studentGroupService;
    }


}
