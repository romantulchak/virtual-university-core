package com.romantulchak.virtualuniversity.contoller;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.dto.SubjectDTO;
import com.romantulchak.virtualuniversity.model.Subject;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.service.impl.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/subject")
public class SubjectController {

    private final SubjectServiceImpl subjectService;

    @Autowired
    public SubjectController(SubjectServiceImpl subjectService){
        this.subjectService = subjectService;
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @JsonView(Views.SubjectView.class)
    public Collection<SubjectDTO> findAllSubjects(){
        return subjectService.findAllSubjects();
    }

    @PostMapping("/createSubject")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public void createSubject(@RequestBody Subject subject){
        subjectService.createSubject(subject);
    }
}
