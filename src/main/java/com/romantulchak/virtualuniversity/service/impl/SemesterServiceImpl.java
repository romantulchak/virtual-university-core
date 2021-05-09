package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.SemesterDTO;
import com.romantulchak.virtualuniversity.exception.SemesterAlreadyExistsException;
import com.romantulchak.virtualuniversity.exception.SemesterIsNullException;
import com.romantulchak.virtualuniversity.exception.SemesterNotFoundException;
import com.romantulchak.virtualuniversity.exception.StudentGradeNotFoundException;
import com.romantulchak.virtualuniversity.model.Semester;
import com.romantulchak.virtualuniversity.repository.SemesterRepository;
import com.romantulchak.virtualuniversity.repository.StudentGroupRepository;
import com.romantulchak.virtualuniversity.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SemesterServiceImpl implements SemesterService {
    private final SemesterRepository semesterRepository;
    private final StudentGroupRepository studentGroupRepository;
    @Autowired
    public SemesterServiceImpl(SemesterRepository semesterRepository, StudentGroupRepository studentGroupRepository) {
        this.semesterRepository = semesterRepository;
        this.studentGroupRepository = studentGroupRepository;
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

    @Deprecated
    @Override
    public SemesterDTO findSemester(long specializationId, int currentSemesterId) {
//        return semesterRepository.findFirstBySpecialization_IdAndSemesterNumber(specializationId, currentSemesterId)
//                .map(this::convertToDTO)
//                .orElseThrow(SemesterNotFoundException::new);
        throw new RuntimeException("Deprecated");
    }

    @Override
    public Collection<SemesterDTO> findSemestersForSpecialization(long id) {
        return semesterRepository.findSemestersForSpecialization(id)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<SemesterDTO> findSemesterForGroup(long groupId) {
        if(studentGroupRepository.existsById(groupId)) {
            Collection<Semester> semesters = semesterRepository.findAllSemesterForGroup(groupId);
            return semesters.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        throw new StudentGradeNotFoundException();
    }

    private SemesterDTO convertToDTO(Semester semester) {
        return new SemesterDTO(semester);
    }


}
