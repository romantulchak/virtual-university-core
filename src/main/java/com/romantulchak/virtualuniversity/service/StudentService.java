package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.StudentDTO;
import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.payload.request.RegistrationRequest;

public interface StudentService {
    Student studentGrade();
    void createStudent(Student student);
    StudentDTO getStudentInformation(long id);
}
