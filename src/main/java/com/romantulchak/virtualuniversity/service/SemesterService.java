package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.SemesterDTO;
import com.romantulchak.virtualuniversity.model.Semester;

import java.util.Collection;

public interface SemesterService {
    void create(Semester semester);
    Collection<SemesterDTO> findAllSemesters();

    SemesterDTO findSemester(long specializationId, int currentSemesterId);
}
