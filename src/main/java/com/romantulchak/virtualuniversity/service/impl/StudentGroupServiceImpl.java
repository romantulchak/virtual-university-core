package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.exception.GroupWithNameAlreadyExistsException;
import com.romantulchak.virtualuniversity.model.StudentGroup;
import com.romantulchak.virtualuniversity.repository.StudentGroupRepository;
import com.romantulchak.virtualuniversity.repository.SubjectTeacherRepository;
import com.romantulchak.virtualuniversity.service.StudentGroupService;
import org.springframework.stereotype.Service;

@Service
public class StudentGroupServiceImpl implements StudentGroupService {

    private final StudentGroupRepository studentGroupRepository;
    private final SubjectTeacherRepository subjectTeacherRepository;
    public StudentGroupServiceImpl(StudentGroupRepository studentGroupRepository, SubjectTeacherRepository subjectTeacherRepository){
        this.studentGroupRepository = studentGroupRepository;
        this.subjectTeacherRepository = subjectTeacherRepository;
    }

    @Override
    public void createGroup(StudentGroup studentGroup) {
        if (!studentGroupRepository.isExistsByName(studentGroup.getName())){
            StudentGroup studentGroupAfterSave = studentGroupRepository.save(studentGroup);
            studentGroup.getSubjectTeacherGroups().forEach(subjectTeacherGroup -> subjectTeacherGroup.setStudentGroup(studentGroupAfterSave));
            subjectTeacherRepository.saveAll(studentGroup.getSubjectTeacherGroups());

        }else {
            throw new GroupWithNameAlreadyExistsException(studentGroup.getName());
        }
    }
}
