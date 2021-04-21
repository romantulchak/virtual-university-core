package com.romantulchak.virtualuniversity.contoller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.romantulchak.virtualuniversity.dto.SubjectDTO;
import com.romantulchak.virtualuniversity.model.Specialization;
import com.romantulchak.virtualuniversity.model.Subject;
import com.romantulchak.virtualuniversity.model.SubjectFile;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.service.impl.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

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
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @JsonView(Views.SubjectView.class)
    public Collection<SubjectDTO> findAllSubjects(){
        return subjectService.findAllSubjects();
    }

    @PostMapping(value = "/createSubject")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public void createSubject(@RequestPart(value = "file", required = false) Collection<MultipartFile> files, @RequestPart("subject") String subjectInString) throws IOException {

        subjectService.createSubject(subjectInString, files);
    }
    
    @GetMapping("/availableSubjects/{id}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @JsonView(Views.SubjectView.class)
    public Collection<SubjectDTO> findSubjectsAvailableForTeacher(@PathVariable("id") long id){
        return subjectService.findSubjectAvailableForTeacher(id);
    }

    @GetMapping("/findTeacherSubjects/{teacherId}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('TEACHER') AND @authComponent.hasPermission(authentication, #id)")
    @JsonView(Views.TeacherSubjectView.class)
    public Collection<SubjectDTO> subjectsForTeacher(@PathVariable("teacherId") long id){
         return subjectService.findTeacherSubjects(id);
    }

    @GetMapping("/findAllForSpecialization/{id}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @JsonView(Views.SubjectView.class)
    public Collection<SubjectDTO> findAllForSpecialization(@PathVariable("id") long id){
        return subjectService.findAllForSpecialization(id);
    }

    @GetMapping("/availableSubject/{id}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @JsonView(Views.SubjectView.class)
    public Collection<SubjectDTO> findAvailableSubjectsForSpecialization(@PathVariable("id") long id){
        return subjectService.findAvailableSubjectsForSpecialization(id);
    }
    @GetMapping("/availableSubjectForGroup/{id}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @JsonView(Views.SubjectView.class)
    public Collection<SubjectDTO> findAvailableSubjectsForGroup(@PathVariable("id") long id){
        return subjectService.findAvailableSubjectsForGroup(id);
    }
    @GetMapping("/getFilesForSubject/{subjectId}")
    public Collection<SubjectFile> getFilesForSubject(@PathVariable("subjectId") long id){
        return subjectService.getFilesForSubject(id);
    }

}
