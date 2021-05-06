package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.SemesterDTO;
import com.romantulchak.virtualuniversity.exception.SemesterAlreadyExistsException;
import com.romantulchak.virtualuniversity.exception.SemesterIsNullException;
import com.romantulchak.virtualuniversity.exception.SemesterNotFoundException;
import com.romantulchak.virtualuniversity.model.Semester;
import com.romantulchak.virtualuniversity.repository.SemesterRepository;
import com.romantulchak.virtualuniversity.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SemesterServiceImpl implements SemesterService {
    private final SemesterRepository semesterRepository;

    @Autowired
    public SemesterServiceImpl(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    @Override
    public SemesterDTO create(Semester semester) {
        if (semester != null) {
            if (!semesterRepository.existsByName(semester.getName())) {
                Semester semesterAfterSave = semesterRepository.save(semester);
                return convertToDTO(semesterAfterSave);
            } else {
                throw new SemesterAlreadyExistsException(semester.getName());
            }
        } else {
            throw new SemesterIsNullException();
        }
    }

    @Override
    public Collection<SemesterDTO> findAllSemesters() {
        return semesterRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public SemesterDTO findSemester(long specializationId, int currentSemesterId) {
        return semesterRepository.findFirstBySpecialization_IdAndSemesterNumber(specializationId, currentSemesterId)
                .map(this::convertToDTO)
                .orElseThrow(SemesterNotFoundException::new);
    }

    @Override
    public Collection<SemesterDTO> findSemestersForSpecialization(long id) {
        return semesterRepository.findSemestersForSpecialization(id)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private SemesterDTO convertToDTO(Semester semester) {
        return new SemesterDTO(semester);
    }


}
