package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.SubjectDTO;

import java.util.Collection;

public interface SubjectService {
    Collection<SubjectDTO> findAllSubjects();
}
