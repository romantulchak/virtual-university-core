package com.romantulchak.virtualuniversity.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.romantulchak.virtualuniversity.dto.SubjectDTO;
import com.romantulchak.virtualuniversity.dto.SubjectTeacherGroupDTO;
import com.romantulchak.virtualuniversity.exception.*;
import com.romantulchak.virtualuniversity.model.*;
import com.romantulchak.virtualuniversity.projection.SubjectFileProjection;
import com.romantulchak.virtualuniversity.repository.SpecializationRepository;
import com.romantulchak.virtualuniversity.repository.SubjectRepository;
import com.romantulchak.virtualuniversity.repository.SubjectTeacherRepository;
import com.romantulchak.virtualuniversity.repository.TeacherRepository;
import com.romantulchak.virtualuniversity.service.SubjectService;
import com.romantulchak.virtualuniversity.utils.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.romantulchak.virtualuniversity.utils.FileUploader.*;

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

    @Transactional
    @Override
    public void createSubject(String subjectInString, Collection<MultipartFile> files) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Subject subject = objectMapper.readValue(subjectInString, Subject.class);
            if (subject != null) {
                if (!subjectRepository.existsByNameAndType(subject.getName(), subject.getType())) {
                    if (files != null && !files.isEmpty())
                        addFilesToSubject(files, subject);

                    subjectRepository.save(subject);
                } else {
                    throw new SubjectAlreadyExistsException(subject.getName(), subject.getType().name());
                }
            } else {
                throw new SubjectIsNullException();
            }

        } catch (IOException e) {
            throw new SubjectIsNullException();
        }

    }

    private void addFilesToSubject(Collection<MultipartFile> files, Subject subject) {
        for (MultipartFile file : files) {
            String fileName = generateNameForFile(Objects.requireNonNull(file.getOriginalFilename()));
            String directory = "subjectFiles";
            String localPathToFile = getLocalPathToFile(path, directory, fileName);
            String subjectFiles = uploadFile(file, path, localPathToFile, directory, fileName);
            SubjectFile subjectFile = new SubjectFile(subjectFiles, LocalDateTime.now(), fileName, localPathToFile);
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
                .sorted()
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
        return subjectRepository.findAvailableSubjectsForGroup(groupId)
                .stream()
                .map(this::convertDTO)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Collection<SubjectFile> getFilesForSubject(long subjectId) {
        Subject subject = subjectRepository.findSubjectFiles(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException(subjectId));
        return subject.getFiles();
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String filename) {
        String localPath = subjectRepository.findLocalPathToFile(filename).orElseThrow(() -> new RuntimeException("File not found"));
        Path file = Paths.get(localPath);
        try {
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(resource.getFile().toPath()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("File not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<SubjectDTO> findAllSubjectsWithTeachers() {
        return subjectRepository.findAllSubjectsWithTeachers()
                .stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<SubjectDTO> findAllSubjectsWithTeachersForSemester(long semesterId) {
        return subjectRepository.findAllSubjectsWithTeachersBySemester(semesterId)
                .stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());
    }


    private SubjectDTO convertDTO(Subject subject) {
        return new SubjectDTO(subject);
    }
}
