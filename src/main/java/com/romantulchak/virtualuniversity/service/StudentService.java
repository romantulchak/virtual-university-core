package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.StudentDTO;
import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.payload.request.RegistrationRequest;
import com.romantulchak.virtualuniversity.payload.request.ResetPasswordRequest;
import org.springframework.security.core.Authentication;

import java.util.Collection;

public interface StudentService {
    void create(Student student);
    StudentDTO getStudentInformation(long id);
    void resetStudentPassword(ResetPasswordRequest resetPasswordRequest);

    Collection<StudentDTO> findStudentByName(String firstName, String lastName);

    Collection<StudentDTO> findStudentsWithoutGroup();

    int getCurrentStudentSemester(long id);
}
