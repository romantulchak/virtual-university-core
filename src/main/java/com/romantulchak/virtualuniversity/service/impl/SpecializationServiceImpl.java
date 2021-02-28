package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.exception.SpecializationIsNullException;
import com.romantulchak.virtualuniversity.model.Specialization;
import com.romantulchak.virtualuniversity.repository.SpecializationRepository;
import com.romantulchak.virtualuniversity.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository specializationRepository;

    @Autowired
    public SpecializationServiceImpl(SpecializationRepository specializationRepository){
        this.specializationRepository = specializationRepository;
    }

    @Override
    public void create(Specialization specialization) {
        if (specialization != null){
            specializationRepository.save(specialization);
        }else {
            throw new SpecializationIsNullException();
        }
    }
}
