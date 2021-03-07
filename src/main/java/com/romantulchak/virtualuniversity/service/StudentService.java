package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.StudentDTO;
import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.payload.request.RegistrationRequest;
import com.romantulchak.virtualuniversity.payload.request.ResetPasswordRequest;
import org.springframework.security.core.Authentication;

public interface StudentService {
    StudentDTO create(Student student);
    StudentDTO getStudentInformation(long id);
    void resetStudentPassword(ResetPasswordRequest resetPasswordRequest);
}
