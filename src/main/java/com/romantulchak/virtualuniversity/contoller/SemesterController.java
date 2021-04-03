package com.romantulchak.virtualuniversity.contoller;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.dto.SemesterDTO;
import com.romantulchak.virtualuniversity.model.Semester;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.service.impl.SemesterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/semester")
public class SemesterController {

    private final SemesterServiceImpl semesterService;
    @Autowired
    public SemesterController(SemesterServiceImpl semesterService){
        this.semesterService = semesterService;
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @JsonView(Views.SemesterView.class)
    public Collection<SemesterDTO> findAllSemesters(){
        return semesterService.findAllSemesters();
    }

    @PostMapping("/createSemester")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public void createSemester(@RequestBody Semester semester){
        semesterService.create(semester);
    }

    @GetMapping("/findSemester/{specializationId}/{currentSemester}")
    @JsonView(Views.SemesterView.class)
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public SemesterDTO findSemester(@PathVariable("specializationId") long specializationId, @PathVariable("currentSemester") int currentSemesterId){
        return semesterService.findSemester(specializationId, currentSemesterId);
    }

    @GetMapping("/findSemestersForSpecialization/{id}")
    @JsonView(Views.SemesterView.class)
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public Collection<SemesterDTO> findSemestersForSpecialization(@PathVariable("id") long id){
        return semesterService.findSemestersForSpecialization(id);
    }
}
