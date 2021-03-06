package com.romantulchak.virtualuniversity.contoller;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.dto.SubjectDTO;
import com.romantulchak.virtualuniversity.dto.pageable.PageableDTO;
import com.romantulchak.virtualuniversity.model.SubjectFile;
import com.romantulchak.virtualuniversity.model.TeacherFileSubject;
import com.romantulchak.virtualuniversity.model.Views;
import com.romantulchak.virtualuniversity.service.impl.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    public void createSubject(@RequestPart(value = "file", required = false) Collection<MultipartFile> files, @RequestPart("subject") String subjectInString) {
        subjectService.createSubject(subjectInString, files);
    }
    
    @GetMapping("/availableSubjects/{id}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @JsonView(Views.SubjectView.class)
    public Collection<SubjectDTO> findSubjectsAvailableForTeacher(@PathVariable("id") long id){
        return subjectService.findSubjectAvailableForTeacher(id);
    }

    @GetMapping("/findTeacherSubjects/{teacherId}/{page}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('TEACHER') AND @authComponent.hasPermission(authentication, #id)")
    @JsonView(Views.TeacherSubjectView.class)
    public PageableDTO<List<SubjectDTO>> subjectsForTeacher(@PathVariable("teacherId") long id, @PathVariable("page") int page){
         return subjectService.findTeacherSubjects(id, page);
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
    @GetMapping("/getFilesForSubject")
    @JsonView(Views.FileView.class)
    public Collection<SubjectFile> getFilesForSubject(@RequestParam(value = "subjectId") long subjectId,
                                                      @RequestParam(value = "groupId") long groupId,
                                                      @RequestParam(value = "semesterId") long semesterId,
                                                      @RequestParam(value = "teacherId") long teacherId){
        return subjectService.getFilesForSubject(subjectId, groupId, semesterId, teacherId);
    }
    @GetMapping(value = "/downloadFile/{filename}", produces= MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        return subjectService.downloadFile(filename);
    }
    @GetMapping("/findAllSubjectsWithTeachers")
    @JsonView(Views.SubjectView.class)
    public Collection<SubjectDTO> findAllSubjectsWithTeachers(){
        return subjectService.findAllSubjectsWithTeachers();
    }

    @GetMapping("/findAllSubjectsWithTeachersForSemester/{semesterId}")
    @JsonView(Views.SubjectView.class)
    public Collection<SubjectDTO> findAllSubjectsWithTeachersForSemester(@PathVariable("semesterId") long semesterId){
        return subjectService.findAllSubjectsWithTeachersForSemester(semesterId);
    }

    @PostMapping("/uploadTeacherFile")
    @PreAuthorize("hasRole('TEACHER') AND @authComponent.hasPermissionToSubject(authentication, #subjectId)")
    public void uploadFile(@RequestPart(value = "file") Collection<MultipartFile> files,
                           @RequestParam(value = "subjectId") long subjectId,
                           @RequestParam(value = "groupId") long groupId,
                           @RequestParam(value = "semesterId") long semesterId,
                           Authentication authentication){
        subjectService.uploadFileForSubject(files, subjectId, groupId, semesterId, authentication);
    }

    @GetMapping("/findTeacherFiles")
    @JsonView(Views.FileView.class)
    @PreAuthorize("hasRole('TEACHER') AND @authComponent.hasPermissionToSubject(authentication, #subjectId)")
    public Collection<SubjectFile> findTeacherFiles(@RequestParam(value = "subjectId") long subjectId,
                                                    @RequestParam(value = "groupId") long groupId,
                                                    @RequestParam(value = "semesterId") long semesterId,
                                                    Authentication authentication){
        return subjectService.findTeacherFiles(subjectId, groupId, semesterId, authentication);
    }
}
