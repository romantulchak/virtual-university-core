package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.StudentGroupDTO;
import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.model.StudentGroup;
import com.romantulchak.virtualuniversity.model.Subject;
import com.romantulchak.virtualuniversity.model.SubjectTeacherGroup;
import com.romantulchak.virtualuniversity.projection.GroupLimited;

import java.util.Collection;
import java.util.List;

public interface StudentGroupService {
    void create(StudentGroup studentGroup);
    StudentGroupDTO findGroupForStudent(long id);
    void addStudentToGroup(List<Student> student, long groupId);

    Collection<StudentGroupDTO> findAllGroups();

    StudentGroupDTO findGroupDetailsById(long id);

    void addSubjectsToGroup(Collection<SubjectTeacherGroup> subjects, long groupId);

    void delete(long groupId);
    Collection<StudentGroupDTO> findGroupsForTeacher(long teacherId);

    StudentGroupDTO findGroupSubjectsForTeacher(long id, long teacherId);


}
