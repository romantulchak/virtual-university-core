package com.romantulchak.virtualuniversity.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.romantulchak.virtualuniversity.dto.SubjectDTO;
import com.romantulchak.virtualuniversity.dto.pageable.PageableDTO;
import com.romantulchak.virtualuniversity.exception.FileNotFoundException;
import com.romantulchak.virtualuniversity.exception.SubjectAlreadyExistsException;
import com.romantulchak.virtualuniversity.exception.SubjectIsNullException;
import com.romantulchak.virtualuniversity.exception.SubjectNotFoundException;
import com.romantulchak.virtualuniversity.model.Subject;
import com.romantulchak.virtualuniversity.model.SubjectFile;
import com.romantulchak.virtualuniversity.model.TeacherFileSubject;
import com.romantulchak.virtualuniversity.projection.SubjectFileProjection;
import com.romantulchak.virtualuniversity.repository.SubjectRepository;
import com.romantulchak.virtualuniversity.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.romantulchak.virtualuniversity.utils.FileUploader.*;
import static com.romantulchak.virtualuniversity.utils.PageUtil.getFrontendPageNumber;

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
            SubjectFile subjectFile = getSubjectFile(file);
            subject.getFiles().add(subjectFile);
        }
    }

    private SubjectFile getSubjectFile(MultipartFile file) {
        String fileName = generateNameForFile(Objects.requireNonNull(file.getOriginalFilename()));
        String directory = "subjectFiles";
        String localPathToFile = getLocalPathToFile(path, directory, fileName);
        String subjectFiles = uploadFile(file, path, localPathToFile, directory, fileName);
        return new SubjectFile(subjectFiles, LocalDateTime.now(), fileName, localPathToFile);
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
    public PageableDTO<List<SubjectDTO>> findTeacherSubjects(long id, int page) {
        Pageable pageable = PageRequest.of(getFrontendPageNumber(page), 3);
        Page<Subject> pageData = subjectRepository.findAllByTeachers_Id(id, pageable);
        List<SubjectDTO> subject = pageData.getContent().stream()
                .map(this::convertDTO)
                .sorted()
                .collect(Collectors.toList());
        return new PageableDTO<>(page, pageData.getTotalPages(), subject);
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
    public Collection<SubjectFile> getFilesForSubject(long subjectId, long groupId, long semesterId, long teacherId) {
        Collection<SubjectFileProjection> files = subjectRepository.findFiles(subjectId);
        Collection<SubjectFileProjection> teacherFiles = subjectRepository.findTeacherFiles(subjectId, groupId, semesterId, teacherId);
        teacherFiles.addAll(files);
        return teacherFiles.stream()
                .map(file -> new SubjectFile(file.getName(), file.getAdded()))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String filename) {
        String localPath = subjectRepository.findLocalPathToFile(filename)
                .orElse(subjectRepository.findLocalPathToTeacherFile(filename));
        Path file = Paths.get(localPath);
        try {
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(resource.getFile().toPath()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new FileNotFoundException();
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

    @Override
    public void uploadFileForSubject(Collection<MultipartFile> files, long subjectId, long groupId, long semesterid,  Authentication authentication) {
        Subject subject = subjectRepository.findSubjectTeacherFiles(subjectId).orElseThrow(() -> new SubjectNotFoundException(subjectId));
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        for (MultipartFile file : files) {
            SubjectFile subjectFile = getSubjectFile(file);
            TeacherFileSubject teacherFile = new TeacherFileSubject(subjectFile, user.getId(), groupId, semesterid);
            subject.getTeacherFileSubjects().add(teacherFile);
        }
        subjectRepository.save(subject);
    }

    @Override
    public Collection<SubjectFile> findTeacherFiles(long subjectId, long groupId, long semesterId, Authentication authentication) {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        Optional<Subject> subject = subjectRepository.findSubjectFilesForTeacher(user.getId(), subjectId, groupId, semesterId);
        return subject
                .map(value -> value.getTeacherFileSubjects()
                        .stream()
                        .map(TeacherFileSubject::getSubjectFile)
                        .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }


    private SubjectDTO convertDTO(Subject subject) {
        return new SubjectDTO(subject);
    }
}
