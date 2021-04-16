package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.SubjectTeacherGroupDTO;
import com.romantulchak.virtualuniversity.repository.SubjectTeacherRepository;
import com.romantulchak.virtualuniversity.service.SubjectTeacherService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
@Service
public class SubjectTeacherServiceImpl implements SubjectTeacherService {

    private final SubjectTeacherRepository subjectTeacherRepository;
    public SubjectTeacherServiceImpl(SubjectTeacherRepository subjectTeacherRepository){
        this.subjectTeacherRepository = subjectTeacherRepository;
    }

    @Override
    public Collection<SubjectTeacherGroupDTO> findGroupSubjects(long groupId) {
        return subjectTeacherRepository.findSubjectsForGroup(groupId)
                .stream()
                .map(SubjectTeacherGroupDTO::new)
                .collect(Collectors.toList());
    }
}