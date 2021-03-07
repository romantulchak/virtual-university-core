package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.SubjectDTO;
import com.romantulchak.virtualuniversity.model.Subject;

import java.util.Collection;

public interface SubjectService {
    Collection<SubjectDTO> findAllSubjects();
    void createSubject(Subject subject);

    Collection<SubjectDTO> findSubjectAvailableForTeacher(long id);
}
