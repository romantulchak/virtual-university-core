package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.SubjectDTO;
import com.romantulchak.virtualuniversity.dto.pageable.PageableDTO;
import com.romantulchak.virtualuniversity.model.SubjectFile;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

public interface SubjectService {
    Collection<SubjectDTO> findAllSubjects();

    void createSubject(String subjectInString, Collection<MultipartFile> files);

    Collection<SubjectDTO> findSubjectAvailableForTeacher(long id);

    PageableDTO<List<SubjectDTO>> findTeacherSubjects(long id, int page);

    Collection<SubjectDTO> findAllForSpecialization(long id);

    Collection<SubjectDTO> findAvailableSubjectsForSpecialization(long id);

    Collection<SubjectDTO> findAvailableSubjectsForGroup(long groupId);

    Collection<SubjectFile> getFilesForSubject(long subjectId);

    ResponseEntity<Resource> downloadFile(String filename);

    Collection<SubjectDTO> findAllSubjectsWithTeachers();

    Collection<SubjectDTO> findAllSubjectsWithTeachersForSemester(long semesterId);

    void uploadFileForSubject(Collection<MultipartFile> file, long subjectId, long groupId, long semesterId, Authentication authentication);
}
