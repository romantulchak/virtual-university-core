package com.romantulchak.virtualuniversity.utils;

import com.romantulchak.virtualuniversity.dto.SubjectDTO;
import com.romantulchak.virtualuniversity.dto.SubjectTeacherGroupDTO;
import com.romantulchak.virtualuniversity.dto.TeacherDTO;
import com.romantulchak.virtualuniversity.model.Subject;
import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.projection.StudentGradeLimitedStudent;

import java.util.Objects;

public final class SubjectTeacherConverter {

    public static SubjectTeacherGroupDTO getSubjectTeacherGroupDTO(long id, Subject subject, Teacher teacher) {
        SubjectTeacherGroupDTO subjectTeacherGroupDTO = new SubjectTeacherGroupDTO(id);
        if (Objects.nonNull(subject)){
            subjectTeacherGroupDTO.setSubject(new SubjectDTO(subject));
        }
        if (Objects.nonNull(teacher)){
            subjectTeacherGroupDTO.setTeacher(new TeacherDTO(teacher));
        }
        return subjectTeacherGroupDTO;
    }
}
