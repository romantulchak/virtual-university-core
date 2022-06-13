package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.SubjectTeacherGroupDTO;
import com.romantulchak.virtualuniversity.model.SubjectTeacherGroup;
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

    //TODO: Sort
    @Override
    public Collection<SubjectTeacherGroupDTO> findGroupSubjects(long groupId, long semesterId) {
        return subjectTeacherRepository.findSubjectsForGroup(groupId, semesterId)
                .stream()
                .map(SubjectTeacherGroupDTO::new)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Collection<SubjectTeacherGroupDTO> getSubjectsTeacherGroup(long groupId, long teacherId, long semesterId) {
        return subjectTeacherRepository.subjectsInGroupForTeacher(groupId, teacherId, semesterId).stream()
                .map(subject -> new SubjectTeacherGroupDTO(subject.getId(),
                        subject.getName(),
                        subject.getType()))
                .collect(Collectors.toList());
    }
    @Override
    public Collection<SubjectTeacherGroupDTO> findSubjectsForGroupBySemester(long semesterId, long groupId) {
        return subjectTeacherRepository.findSubjectsForGroupBySemester(groupId, semesterId).stream()
                .map(SubjectTeacherGroupDTO::new)
                .collect(Collectors.toList());
    }

}
