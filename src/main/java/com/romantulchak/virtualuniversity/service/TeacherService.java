package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.payload.request.ResetPasswordRequest;

public interface TeacherService{
    void createTeacher(Teacher teacher);
    void resetPassword(ResetPasswordRequest resetPasswordRequest);
}
