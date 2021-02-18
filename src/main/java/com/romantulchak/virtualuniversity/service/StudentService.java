package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.payload.request.RegistrationRequest;

public interface StudentService {
    Student studentGrade();
    void createStudent(Student student);
}
