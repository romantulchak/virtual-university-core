package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.SpecializationDTO;
import com.romantulchak.virtualuniversity.model.Specialization;

import java.util.Collection;

public interface SpecializationService {
    void create(Specialization specialization);
    Collection<SpecializationDTO> findAllSpecializationsForStudent(long studentId);
}
