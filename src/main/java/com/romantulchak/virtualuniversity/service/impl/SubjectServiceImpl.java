package com.romantulchak.virtualuniversity.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.romantulchak.virtualuniversity.dto.SubjectDTO;
import com.romantulchak.virtualuniversity.exception.SpecializationNotFoundException;
import com.romantulchak.virtualuniversity.exception.SubjectIsNullException;
import com.romantulchak.virtualuniversity.exception.TeacherNotFoundException;
import com.romantulchak.virtualuniversity.model.Specialization;
import com.romantulchak.virtualuniversity.model.Subject;
import com.romantulchak.virtualuniversity.model.SubjectFile;
import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.repository.SpecializationRepository;
import com.romantulchak.virtualuniversity.repository.SubjectRepository;
import com.romantulchak.virtualuniversity.repository.TeacherRepository;
import com.romantulchak.virtualuniversity.service.SubjectService;
import com.romantulchak.virtualuniversity.utils.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.romantulchak.virtualuniversity.utils.FileUploader.uploadFile;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    @Value("${subject.upload.path}")
    private String path;
    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Collection<SubjectDTO> findAllSubjects() {
        return subjectRepository.findAllWithoutTeachers()
                .stream()
                .map(subjectLimited ->
                        new SubjectDTO(subjectLimited.getId(),
                                subjectLimited.getName(),
                                subjectLimited.getType()))
                .collect(Collectors.toList());
    }

    @Override
    public void createSubject(String subjectInString, Collection<MultipartFile> files) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Subject subject = objectMapper.readValue(subjectInString, Subject.class);
            if (subject != null) {
                addFilesToSubject(files, subject);
                subjectRepository.save(subject);
            } else {
                throw new SubjectIsNullException();
            }

        }catch (IOException e){
            throw new SubjectIsNullException();
        }

    }

    private void addFilesToSubject(Collection<MultipartFile> files, Subject subject) {
        for (MultipartFile file: files) {
            String subjectFiles = uploadFile(file, path, "subjectFiles");
            SubjectFile subjectFile = new SubjectFile(subjectFiles, LocalDateTime.now(), subjectFiles.substring(0, 41));
            subject.getFiles().add(subjectFile);
        }
    }

    @Override
    public Collection<SubjectDTO> findSubjectAvailableForTeacher(long id) {
        return subjectRepository.findAvailableSubjectsForTeacher(id)
                .stream()
                .map(subjectLimited ->
                        new SubjectDTO(subjectLimited.getId(),
                                subjectLimited.getName(),
                                subjectLimited.getType()))
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Collection<SubjectDTO> findTeacherSubjects(long id) {
        return subjectRepository.findAllByTeachers_Id(id)
                .stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<SubjectDTO> findAllForSpecialization(long id) {
        return subjectRepository.findAllForSpecialization(id)
                .stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<SubjectDTO> findAvailableSubjectsForSpecialization(long id) {
        List<Subject> allSubjects = subjectRepository.findAll();
        allSubjects.removeAll(subjectRepository.findAllForSpecialization(id));
        return allSubjects.stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<SubjectDTO> findAvailableSubjectsForGroup(long groupId) {
        return subjectRepository.findAvailableSubjectsForGroup(groupId).stream().map(this::convertDTO).sorted().collect(Collectors.toList());
    }

    private SubjectDTO convertDTO(Subject subject) {
        return new SubjectDTO(subject);
    }
}
