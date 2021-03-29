package com.romantulchak.virtualuniversity.components;

import com.romantulchak.virtualuniversity.repository.StudentGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccessToStudentGroup {

    private final StudentGroupRepository studentGroupRepository;

    @Autowired
    public AccessToStudentGroup(StudentGroupRepository studentGroupRepository){
        this.studentGroupRepository = studentGroupRepository;
    }

    public boolean checkAccess(long teacherId){
       return studentGroupRepository.hasAccess(teacherId);
    }

}
