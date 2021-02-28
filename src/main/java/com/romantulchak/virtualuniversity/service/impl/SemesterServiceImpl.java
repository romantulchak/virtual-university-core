package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.exception.SemesterIsNullException;
import com.romantulchak.virtualuniversity.model.Semester;
import com.romantulchak.virtualuniversity.repository.SemesterRepository;
import com.romantulchak.virtualuniversity.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SemesterServiceImpl implements SemesterService {
    private final SemesterRepository semesterRepository;
    @Autowired
    public SemesterServiceImpl(SemesterRepository semesterRepository){
        this.semesterRepository = semesterRepository;
    }
    @Override
    public void create(Semester semester) {
        if (semester != null){
            semesterRepository.save(semester);
        }else {
            throw new SemesterIsNullException();
        }
    }
}
