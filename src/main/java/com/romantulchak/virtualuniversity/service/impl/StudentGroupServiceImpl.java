package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.SemesterDTO;
import com.romantulchak.virtualuniversity.dto.StudentDTO;
import com.romantulchak.virtualuniversity.dto.StudentGroupDTO;
import com.romantulchak.virtualuniversity.dto.SubjectTeacherGroupDTO;
import com.romantulchak.virtualuniversity.exception.GroupForStudentNotFoundException;
import com.romantulchak.virtualuniversity.exception.GroupNotFoundException;
import com.romantulchak.virtualuniversity.exception.GroupWithNameAlreadyExistsException;
import com.romantulchak.virtualuniversity.exception.StudentAlreadyHasGroupException;
import com.romantulchak.virtualuniversity.model.Semester;
import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.model.StudentGroup;
import com.romantulchak.virtualuniversity.model.SubjectTeacherGroup;
import com.romantulchak.virtualuniversity.projection.GroupDetailsLimited;
import com.romantulchak.virtualuniversity.projection.GroupLimited;
import com.romantulchak.virtualuniversity.repository.StudentGroupRepository;
import com.romantulchak.virtualuniversity.repository.SubjectTeacherRepository;
import com.romantulchak.virtualuniversity.service.StudentGroupService;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentGroupServiceImpl implements StudentGroupService {

    private final StudentGroupRepository studentGroupRepository;
    private final SubjectTeacherRepository subjectTeacherRepository;
    public StudentGroupServiceImpl(StudentGroupRepository studentGroupRepository, SubjectTeacherRepository subjectTeacherRepository){
        this.studentGroupRepository = studentGroupRepository;
        this.subjectTeacherRepository = subjectTeacherRepository;
    }

    @Override
    public void createGroup(StudentGroup studentGroup) {
        if (!studentGroupRepository.isExistsByName(studentGroup.getName())){
            StudentGroup studentGroupAfterSave = studentGroupRepository.save(studentGroup);
            studentGroup.getSubjectTeacherGroups().forEach(subjectTeacherGroup -> subjectTeacherGroup.setStudentGroup(studentGroupAfterSave));
            subjectTeacherRepository.saveAll(studentGroup.getSubjectTeacherGroups());

        }else {
            throw new GroupWithNameAlreadyExistsException(studentGroup.getName());
        }
    }

    @Override
    public StudentGroupDTO findGroupForStudent(long id) {
        return studentGroupRepository.findByStudents_Id(id)
                                     .map(this::convertToDTO)
                                     .orElseThrow(() -> new GroupForStudentNotFoundException(id));
    }

    @Override
    public void addStudentToGroup(List<Student> students, long groupId) {
        StudentGroup group = studentGroupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
        List<Student> studentsWithGroup = new ArrayList<>();
        group.getStudents().forEach(student -> {
            if (students.contains(student)){
                studentsWithGroup.add(student);
            }
        });
        students.removeAll(studentsWithGroup);
        group.getStudents().addAll(students);
        studentGroupRepository.save(group);
        if (studentsWithGroup.size() != 0){
                throw new StudentAlreadyHasGroupException(students);
        }
    }

    @Override
    public Collection<StudentGroupDTO> findAllGroups() {
        return studentGroupRepository.allGroups().stream().
                map(studentGroup -> convertToDTO(studentGroup.getId(), studentGroup.getName(), studentGroup.getSemester()))
                .collect(Collectors.toList());

    }

    @Override
    public StudentGroupDTO findGroupDetailsById(long id) {
        StudentGroup studentGroup = studentGroupRepository.groupById(id).orElseThrow(() -> new GroupNotFoundException(id));
        Collection<StudentDTO> students = studentGroup.getStudents().stream().map(StudentDTO::new).collect(Collectors.toList());
        Collection<SubjectTeacherGroupDTO> subjects = studentGroup.getSubjectTeacherGroups().stream().map(SubjectTeacherGroupDTO::new).collect(Collectors.toList());
        return new StudentGroupDTO(studentGroup.getName(), students, subjects);
    }

    private StudentGroupDTO convertToDTO(long id, String name, Semester semester){
        return new StudentGroupDTO(id, name, new SemesterDTO(semester));
    }

    private StudentGroupDTO convertToDTO(StudentGroup studentGroup){
        List<SubjectTeacherGroupDTO> subjects = studentGroup.getSubjectTeacherGroups() != null ? studentGroup.getSubjectTeacherGroups().stream().map(SubjectTeacherGroupDTO::new).collect(Collectors.toList()) : new ArrayList<>();
        return new StudentGroupDTO(studentGroup, subjects);
    }

}
