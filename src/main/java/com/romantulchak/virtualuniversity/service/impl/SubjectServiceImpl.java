package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.SubjectDTO;
import com.romantulchak.virtualuniversity.exception.SubjectIsNullException;
import com.romantulchak.virtualuniversity.exception.TeacherNotFoundException;
import com.romantulchak.virtualuniversity.model.Subject;
import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.repository.SubjectRepository;
import com.romantulchak.virtualuniversity.repository.TeacherRepository;
import com.romantulchak.virtualuniversity.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    public SubjectServiceImpl(SubjectRepository subjectRepository, TeacherRepository teacherRepository){
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
    }
    @Override
    public Collection<SubjectDTO> findAllSubjects() {
        return subjectRepository.findAll().stream().map(this::convertDTO).collect(Collectors.toList());
    }

    @Override
    public void createSubject(Subject subject) {
        if (subject != null){
            subjectRepository.save(subject);
        }else {
            throw new SubjectIsNullException();
        }
    }

    @Override
    public Collection<SubjectDTO> findSubjectAvailableForTeacher(long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException(id));
        Collection<Subject> subjects = subjectRepository.findAll();
        subjects.removeAll(teacher.getSubjects());
        return subjects.stream().map(this::convertDTO).collect(Collectors.toList());
    }

    private SubjectDTO convertDTO(Subject subject){
        return new SubjectDTO(subject);
    }
}
