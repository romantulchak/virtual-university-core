package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.*;
import com.romantulchak.virtualuniversity.exception.GroupForStudentNotFoundException;
import com.romantulchak.virtualuniversity.exception.GroupNotFoundException;
import com.romantulchak.virtualuniversity.exception.GroupWithNameAlreadyExistsException;
import com.romantulchak.virtualuniversity.exception.StudentAlreadyHasGroupException;
import com.romantulchak.virtualuniversity.model.*;
import com.romantulchak.virtualuniversity.projection.GroupLimited;
import com.romantulchak.virtualuniversity.projection.GroupStudentsLimited;
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
        long groupId = studentGroupRepository.findGroupIdByStudentId(id);
        GroupStudentsLimited group = studentGroupRepository.findByStudents_Id(groupId).orElseThrow(()-> new GroupNotFoundException(id));
        return new StudentGroupDTO.Builder(group.getId(), group.getName())
                                                             .withCounter(group.getCount())
                                                             .withSpecialization(group.getSpecialization())
                                                             .build();

    }

    @Override
    public void addStudentToGroup(List<Student> students, long groupId) {
        StudentGroup group = studentGroupRepository.findGroupById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
        List<Student> studentsWithGroup = getStudentsWithGroup(students, group);
        students.removeAll(studentsWithGroup);
        group.getStudents().addAll(students);
        studentGroupRepository.save(group);
        if (studentsWithGroup.size() != 0) {
            throw new StudentAlreadyHasGroupException(students);
        }
    }

    private List<Student> getStudentsWithGroup(List<Student> students, StudentGroup group) {
        List<Student> studentsWithGroup = new ArrayList<>();
        group.getStudents().forEach(student -> {
            if (students.contains(student)) {
                studentsWithGroup.add(student);
            }
        });
        return studentsWithGroup;
    }

    @Override
    public Collection<StudentGroupDTO> findAllGroups() {
       return studentGroupRepository.allGroups()
                                    .stream()
                                    .map(group -> convertToDTO(group.getId(), group.getName(), group.getSemester()))
                                    .collect(Collectors.toList());
    }

    @Override
    public StudentGroupDTO findGroupDetailsById(long id) {
        StudentGroup studentGroup = studentGroupRepository.groupByIdWithSubjectsAndStudents(id).orElseThrow(() -> new GroupNotFoundException(id));
        return new StudentGroupDTO.Builder(studentGroup.getId(), studentGroup.getName())
                                                             .withStudents(studentGroup.getStudents())
                                                             .withSubjects(studentGroup.getSubjectTeacherGroups())
                                                             .withSpecialization(studentGroup.getSpecialization())
                                                             .withCounter(studentGroup.getStudents().size())
                                                             .build();
    }

    @Override
    public void addSubjectsToGroup(Collection<SubjectTeacherGroup> subjects, long groupId) {
        StudentGroup studentGroup = studentGroupRepository.groupByIdWithSubjectsAndStudents(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
        subjects.forEach(x-> subjectTeacherRepository.saveSubjectTeacherGroup(studentGroup.getId(), x.getSubject().getId(), x.getTeacher().getId()));
    }

    @Override
    public void delete(long groupId) {
        studentGroupRepository.deleteById(groupId);
    }

    @Override
    public Collection<StudentGroupDTO> findGroupsForTeacher(long teacherId) {
        return studentGroupRepository.findGroupsForTeacher(teacherId).stream()
                                .map(studentGroup -> convertToDTO(studentGroup.getId(), studentGroup.getName(), studentGroup.getSemester()))
                                .sorted()
                                .collect(Collectors.toList());

    }

    @Override
    public StudentGroupDTO findGroupSubjectsForTeacher(long id, long teacherId) {
        return null;
    }

    private StudentGroupDTO convertToDTO(long id, String name, Semester semester) {
        return new StudentGroupDTO.Builder(id, name)
                                  .withSemester(semester)
                                  .build();
    }



}
