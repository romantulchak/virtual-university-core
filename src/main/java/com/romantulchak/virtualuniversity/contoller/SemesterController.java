package com.romantulchak.virtualuniversity.contoller;

import com.romantulchak.virtualuniversity.model.Semester;
import com.romantulchak.virtualuniversity.service.impl.SemesterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/semester")
public class SemesterController {

    private final SemesterServiceImpl semesterService;
    @Autowired
    public SemesterController(SemesterServiceImpl semesterService){
        this.semesterService = semesterService;
    }
    @PostMapping("/createSemester")
    public void createSemester(@RequestBody Semester semester){
        semesterService.create(semester);
    }

}
