package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.TeacherDTO;
import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.payload.request.ResetPasswordRequest;

import java.util.Collection;

public interface TeacherService{
    void create(Teacher teacher);
    void resetPassword(ResetPasswordRequest resetPasswordRequest);
    Collection<TeacherDTO> findAllTeachers();
}
