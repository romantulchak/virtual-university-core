package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.SpecializationDTO;
import com.romantulchak.virtualuniversity.model.Specialization;
import com.romantulchak.virtualuniversity.model.Subject;

import java.util.Collection;

public interface SpecializationService {
    void create(Specialization specialization);
    Collection<SpecializationDTO> findAllSpecializationsForStudent(long studentId);
    Collection<SpecializationDTO> findALlSpecializationsForTeacher(long teacherId);


    Collection<SpecializationDTO> findAllSpecializations();

    void addSubjectsToSpecialization(Collection<Subject> subjects, long specializationId);
}
