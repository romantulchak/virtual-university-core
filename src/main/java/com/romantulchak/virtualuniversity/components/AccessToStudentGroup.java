package com.romantulchak.virtualuniversity.components;

import com.romantulchak.virtualuniversity.repository.StudentGroupGradeRepository;
import com.romantulchak.virtualuniversity.repository.StudentGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccessToStudentGroup {

    private final StudentGroupRepository studentGroupRepository;
    private final StudentGroupGradeRepository studentGroupGradeRepository;
    @Autowired
    public AccessToStudentGroup(StudentGroupRepository studentGroupRepository,
                                StudentGroupGradeRepository studentGroupGradeRepository){
        this.studentGroupRepository = studentGroupRepository;
        this.studentGroupGradeRepository = studentGroupGradeRepository;
    }

    public boolean checkAccess(long teacherId){
       return studentGroupRepository.hasAccess(teacherId);
    }

    public boolean checkAccessToGrades(long studentId){
        return studentGroupGradeRepository.hasAccessToGrades(studentId);
    }

}
