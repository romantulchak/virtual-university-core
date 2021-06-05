package com.romantulchak.virtualuniversity.components;

import com.romantulchak.virtualuniversity.model.StudentGroupGrade;
import com.romantulchak.virtualuniversity.payload.request.SetGradeRequest;
import com.romantulchak.virtualuniversity.repository.StudentGroupGradeRepository;
import com.romantulchak.virtualuniversity.repository.StudentGroupRepository;
import com.romantulchak.virtualuniversity.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

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

    public boolean checkAccessToSetGrade(Collection<StudentGroupGrade> studentGroupGrade, Authentication authentication){
        if(authentication != null) {
            UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
            for (StudentGroupGrade groupGrade : studentGroupGrade) {
                boolean hasAccess = studentGroupGradeRepository.hasAccessSetGrade(principal.getId(), groupGrade.getId());
                if(!hasAccess){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean checkAccessToGrades(long studentId){
        return studentGroupGradeRepository.hasAccessToGrades(studentId);
    }

}
