package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.StudentGroupDTO;
import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.model.StudentGroup;
import com.romantulchak.virtualuniversity.projection.GroupLimited;

import java.util.Collection;
import java.util.List;

public interface StudentGroupService {
    void createGroup(StudentGroup studentGroup);
    StudentGroupDTO findGroupForStudent(long id);
    void addStudentToGroup(List<Student> student, long groupId);

    Collection<StudentGroupDTO> findAllGroups();

    StudentGroupDTO findGroupDetailsById(long id);
}
