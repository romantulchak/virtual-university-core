package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.SubjectDTO;
import com.romantulchak.virtualuniversity.dto.SubjectTeacherGroupDTO;
import com.romantulchak.virtualuniversity.model.Specialization;
import com.romantulchak.virtualuniversity.model.Subject;
import com.romantulchak.virtualuniversity.model.SubjectFile;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface SubjectService {
    Collection<SubjectDTO> findAllSubjects();

    void createSubject(String subjectInString, Collection<MultipartFile> files);

    Collection<SubjectDTO> findSubjectAvailableForTeacher(long id);

    Collection<SubjectDTO> findTeacherSubjects(long id);

    Collection<SubjectDTO> findAllForSpecialization(long id);

    Collection<SubjectDTO> findAvailableSubjectsForSpecialization(long id);

    Collection<SubjectDTO> findAvailableSubjectsForGroup(long groupId);

    Collection<SubjectFile> getFilesForSubject(long subjectId);

    ResponseEntity<Resource> downloadFile(String filename);

    Collection<SubjectDTO> findAllSubjectsWithTeachers();

    Collection<SubjectDTO> findAllSubjectsWithTeachersForSemester(long semesterId);
}
