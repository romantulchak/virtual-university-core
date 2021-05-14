package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.StudentGroupDTO;
import com.romantulchak.virtualuniversity.dto.SubjectTeacherGroupDTO;

import java.util.Collection;

public interface SubjectTeacherService {
    Collection<SubjectTeacherGroupDTO> findGroupSubjects(long groupId, long semesterId);

    Collection<SubjectTeacherGroupDTO> getSubjectsTeacherGroup(long groupId, long teacherId, long semesterId);

    Collection<SubjectTeacherGroupDTO> findSubjectsForGroupBySemester(long semesterId, long groupId);

}
