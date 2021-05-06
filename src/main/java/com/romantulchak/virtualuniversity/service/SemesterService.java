package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.SemesterDTO;
import com.romantulchak.virtualuniversity.model.Semester;

import java.util.Collection;

public interface SemesterService {
    SemesterDTO create(Semester semester);
    Collection<SemesterDTO> findAllSemesters();

    SemesterDTO findSemester(long specializationId, int currentSemesterId);

    Collection<SemesterDTO> findSemestersForSpecialization(long id);
}
