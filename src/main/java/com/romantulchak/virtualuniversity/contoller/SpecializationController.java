package com.romantulchak.virtualuniversity.contoller;

import com.romantulchak.virtualuniversity.model.Specialization;
import com.romantulchak.virtualuniversity.service.impl.SpecializationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/specialization")
public class SpecializationController {

    private final SpecializationServiceImpl specializationService;
    @Autowired
    public SpecializationController(SpecializationServiceImpl specializationService){
        this.specializationService = specializationService;
    }

    @PostMapping("/createSpecialization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public void createSpecialization(@RequestBody Specialization specialization){
        this.specializationService.create(specialization);
    }
}
