package com.romantulchak.virtualuniversity.contoller;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.virtualuniversity.dto.StudentGroupDTO;
import com.romantulchak.virtualuniversity.dto.SubjectTeacherGroupDTO;
import com.romantulchak.virtualuniversity.model.*;
import com.romantulchak.virtualuniversity.service.SubjectTeacherService;
import com.romantulchak.virtualuniversity.service.impl.StudentGroupServiceImpl;
import com.romantulchak.virtualuniversity.service.impl.SubjectTeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/student-group")
public class StudentGroupController {

    private final StudentGroupServiceImpl studentGroupService;
    private final SubjectTeacherServiceImpl subjectTeacherService;

    @Autowired
    public StudentGroupController(StudentGroupServiceImpl studentGroupService, SubjectTeacherServiceImpl subjectTeacherService){
        this.studentGroupService = studentGroupService;
        this.subjectTeacherService = subjectTeacherService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public void create(@RequestBody StudentGroup studentGroup){
        studentGroupService.create(studentGroup);
    }

    @PutMapping("/addStudents/{groupId}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public void addStudentsToGroup(@RequestBody List<Student> students, @PathVariable("groupId") long groupId){
        studentGroupService.addStudentToGroup(students, groupId);
    }
    @GetMapping("/findStudentGroup/{id}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('STUDENT') AND @authComponent.hasPermission(authentication, #id)")
    @JsonView(Views.StudentGroupView.class)
    public StudentGroupDTO findStudentGroup(@PathVariable("id") long id){
        return studentGroupService.findGroupForStudent(id);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @JsonView(Views.StudentGroupView.class)
    public Collection<StudentGroupDTO> findAllGroups(){
        return studentGroupService.findAllGroups();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @JsonView(Views.StudentGroupView.class)
    public StudentGroupDTO findGroupDetailsById(@PathVariable("id") long id){
        return studentGroupService.findGroupDetailsById(id);
    }
    @GetMapping("/{id}/{teacherId}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER') OR @accessToStudentGroup.checkAccess(#teacherId)")
    @JsonView(Views.StudentGroupView.class)
    public StudentGroupDTO findGroupSubjectsForTeacher(@PathVariable("id") long id, @PathVariable("teacherId") long teacherId){
        return studentGroupService.findGroupSubjectsForTeacher(id, teacherId);
    }

    @PutMapping("/addSubjects/{groupId}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    public void addSubjects(@RequestBody Collection<SubjectTeacherGroup> subjects, @PathVariable("groupId") long groupId){
        studentGroupService.addSubjectsToGroup(subjects, groupId);
    }

    @DeleteMapping("/deleteGroup/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteGroup(@PathVariable("id") long id){
        studentGroupService.delete(id);
    }

    @GetMapping("/groupsForTeacher/{id}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('TEACHER') AND @authComponent.hasPermission(authentication, #id)")
    @JsonView(Views.StudentGroupView.class)
    public Collection<StudentGroupDTO> findGroupsForTeacher(@PathVariable("id") long id){
        return studentGroupService.findGroupsForTeacher(id);
    }
    @DeleteMapping("/deleteStudentFromGroup")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStudentFromGroup(@RequestParam(value = "groupId") long groupId, @RequestParam(value = "studentId") long studentId){
        studentGroupService.deleteStudentFromGroup(groupId, studentId);
    }

    @GetMapping("/findSubjectsForGroup/{groupId}")
    @JsonView(Views.StudentGroupView.class)
    public Collection<SubjectTeacherGroupDTO> findSubjectsForGroup(@PathVariable("groupId") long groupId){
        return subjectTeacherService.findGroupSubjects(groupId);
    }

    @PutMapping("/changeGroupSemester")
    public void changeGroupSemester(@RequestParam(value = "groupId") long groupId, @RequestParam(value = "semesterId") long semesterId, @RequestBody List<SubjectTeacherGroup> subjects){
        studentGroupService.changeGroupSemester(groupId, semesterId, subjects);
    }
}
