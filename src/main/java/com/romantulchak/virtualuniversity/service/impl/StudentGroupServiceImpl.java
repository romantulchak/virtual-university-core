package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.*;
import com.romantulchak.virtualuniversity.exception.GroupForStudentNotFoundException;
import com.romantulchak.virtualuniversity.exception.GroupNotFoundException;
import com.romantulchak.virtualuniversity.exception.GroupWithNameAlreadyExistsException;
import com.romantulchak.virtualuniversity.exception.StudentAlreadyHasGroupException;
import com.romantulchak.virtualuniversity.model.*;
import com.romantulchak.virtualuniversity.repository.StudentGroupRepository;
import com.romantulchak.virtualuniversity.repository.SubjectTeacherRepository;
import com.romantulchak.virtualuniversity.service.StudentGroupService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentGroupServiceImpl implements StudentGroupService {

    private final StudentGroupRepository studentGroupRepository;
    private final SubjectTeacherRepository subjectTeacherRepository;

    public StudentGroupServiceImpl(StudentGroupRepository studentGroupRepository, SubjectTeacherRepository subjectTeacherRepository) {
        this.studentGroupRepository = studentGroupRepository;
        this.subjectTeacherRepository = subjectTeacherRepository;
    }

    @Override
    public void create(StudentGroup studentGroup) {
        if (!studentGroupRepository.isExistsByName(studentGroup.getName())) {
            StudentGroup studentGroupAfterSave = studentGroupRepository.save(studentGroup);
            studentGroup.getSubjectTeacherGroups().forEach(subjectTeacherGroup -> subjectTeacherGroup.setStudentGroup(studentGroupAfterSave));
            subjectTeacherRepository.saveAll(studentGroup.getSubjectTeacherGroups());
        } else {
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
        StudentGroup group = studentGroupRepository.findGroupById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
        List<Student> studentsWithGroup = new ArrayList<>();
        group.getStudents().forEach(student -> {
            if (students.contains(student)) {
                studentsWithGroup.add(student);
            }
        });
        students.removeAll(studentsWithGroup);
        group.getStudents().addAll(students);
        studentGroupRepository.save(group);
        if (studentsWithGroup.size() != 0) {
            throw new StudentAlreadyHasGroupException(students);
        }
    }

    @Override
    public Collection<StudentGroupDTO> findAllGroups() {
        return studentGroupRepository.allGroups().stream().
                map(studentGroup -> convertToDTO(studentGroup.getId(), studentGroup.getName(), studentGroup.getSemester()))
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public StudentGroupDTO findGroupDetailsById(long id) {
        StudentGroup studentGroup = studentGroupRepository.groupByIdWithSubjectsAndStudents(id).orElseThrow(() -> new GroupNotFoundException(id));
        Collection<StudentDTO> students = studentGroup.getStudents()
                .stream()
                .map(StudentDTO::new)
                .sorted()
                .collect(Collectors.toList());
        Collection<SubjectTeacherGroupDTO> subjects = studentGroup.getSubjectTeacherGroups()
                .stream().map(SubjectTeacherGroupDTO::new)
                .sorted(Comparator
                        .comparing(subjectTeacherGroupDTO -> subjectTeacherGroupDTO.getSubject().getName()))
                .collect(Collectors.toList());
        return new StudentGroupDTO(studentGroup.getName(), students, subjects, new SpecializationDTO(studentGroup.getSpecialization()));
    }

    private StudentGroupDTO convertToDTO(long id, String name, Semester semester) {
        return new StudentGroupDTO(id, name, new SemesterDTO(semester));
    }

    @Override
    public void addSubjectsToGroup(Collection<SubjectTeacherGroup> subjects, long groupId) {
        StudentGroup studentGroup = studentGroupRepository.groupByIdWithSubjectsAndStudents(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
        subjects.forEach(x-> {
            subjectTeacherRepository.saveSubjectTeacherGroup(studentGroup.getId(), x.getSubject().getId(), x.getTeacher().getId());
        });
    }

    @Override
    public void delete(long groupId) {
        studentGroupRepository.deleteById(groupId);
    }

    private StudentGroupDTO convertToDTO(StudentGroup studentGroup) {
        List<SubjectTeacherGroupDTO> subjects = studentGroup.getSubjectTeacherGroups() != null ? studentGroup.getSubjectTeacherGroups().stream().map(SubjectTeacherGroupDTO::new).collect(Collectors.toList()) : new ArrayList<>();
        return new StudentGroupDTO(studentGroup, subjects);
    }

}
