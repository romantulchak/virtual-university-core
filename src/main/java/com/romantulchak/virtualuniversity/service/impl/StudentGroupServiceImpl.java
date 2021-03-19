package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.model.StudentGroup;
import com.romantulchak.virtualuniversity.repository.StudentGroupRepository;
import com.romantulchak.virtualuniversity.service.StudentGroupService;
import org.springframework.stereotype.Service;

@Service
public class StudentGroupServiceImpl implements StudentGroupService {

    private final StudentGroupRepository studentGroupRepository;

    public StudentGroupServiceImpl(StudentGroupRepository studentGroupRepository){
        this.studentGroupRepository = studentGroupRepository;
    }

    @Override
    public void createGroup(StudentGroup studentGroup) {
        if (studentGroup != null){

        }
    }
}
