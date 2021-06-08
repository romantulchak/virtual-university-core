package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.SpecializationDTO;
import com.romantulchak.virtualuniversity.exception.SemesterNotFoundException;
import com.romantulchak.virtualuniversity.exception.SpecializationIsNullException;
import com.romantulchak.virtualuniversity.exception.SpecializationNotFoundException;
import com.romantulchak.virtualuniversity.exception.SpecializationSemesterAlreadyExists;
import com.romantulchak.virtualuniversity.model.Semester;
import com.romantulchak.virtualuniversity.model.Specialization;
import com.romantulchak.virtualuniversity.model.Subject;
import com.romantulchak.virtualuniversity.repository.SemesterRepository;
import com.romantulchak.virtualuniversity.repository.SpecializationRepository;
import com.romantulchak.virtualuniversity.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository specializationRepository;
    private final SemesterRepository semesterRepository;

    @Autowired
    public SpecializationServiceImpl(SpecializationRepository specializationRepository, SemesterRepository semesterRepository) {
        this.specializationRepository = specializationRepository;
        this.semesterRepository = semesterRepository;
    }

    @Override
    public void create(Specialization specialization) {
        if (specialization != null) {
            specializationRepository.save(specialization);
        } else {
            throw new SpecializationIsNullException();
        }
    }

    @Override
    public Collection<SpecializationDTO> findAllSpecializationsForStudent(long studentId) {
        return specializationRepository.findAllByStudents_id(studentId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<SpecializationDTO> findALlSpecializationsForTeacher(long teacherId) {
        return specializationRepository.findAllByTeachers_Id(teacherId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<SpecializationDTO> findAllSpecializations() {
        return specializationRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void addSubjectsToSpecialization(Collection<Subject> subjects, long specializationId) {
        if (subjects.size() != 0) {
            Specialization specialization = specializationRepository.findSpecializationById(specializationId).orElseThrow(() -> new SpecializationNotFoundException(specializationId));
            subjects.removeAll(specialization.getSubjects());
            if (subjects.size() != 0){
                specialization.getSubjects().addAll(subjects);
                specializationRepository.save(specialization);
            }
        }
    }

    private SpecializationDTO convertToDTO(Specialization specialization) {
        return new SpecializationDTO(specialization);
    }
}
