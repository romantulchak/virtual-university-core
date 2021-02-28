package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.SubjectDTO;
import com.romantulchak.virtualuniversity.model.Subject;
import com.romantulchak.virtualuniversity.repository.SubjectRepository;
import com.romantulchak.virtualuniversity.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository){
        this.subjectRepository = subjectRepository;
    }
    @Override
    public Collection<SubjectDTO> findAllSubjects() {
        return subjectRepository.findAll().stream().map(this::convertDTO).collect(Collectors.toList());
    }

    private SubjectDTO convertDTO(Subject subject){
        return new SubjectDTO(subject);
    }
}
