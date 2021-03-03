package com.romantulchak.virtualuniversity.contoller;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.dto.SpecializationDTO;
import com.romantulchak.virtualuniversity.model.Specialization;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.service.impl.SpecializationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/specialization")
public class SpecializationController {

    private final SpecializationServiceImpl specializationService;
    @Autowired
    public SpecializationController(SpecializationServiceImpl specializationService){
        this.specializationService = specializationService;
    }

    @GetMapping("/specializationForStudent/{id}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('STUDENT') AND @authComponent.hasPermission(authentication, #id)")
    @JsonView(Views.SpecializationView.class)
    public Collection<SpecializationDTO> findAllSpecializationForStudent(@PathVariable("id") long id){
        return specializationService.findAllSpecializationsForStudent(id);
    }

    @PostMapping("/createSpecialization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public void createSpecialization(@RequestBody Specialization specialization){
        this.specializationService.create(specialization);
    }
}
