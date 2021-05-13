package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.StudentGroupDTO;
import com.romantulchak.virtualuniversity.dto.SubjectTeacherGroupDTO;

import java.util.Collection;

public interface SubjectTeacherService {
    Collection<SubjectTeacherGroupDTO> findGroupSubjects(long groupId, long semesterId);
}
