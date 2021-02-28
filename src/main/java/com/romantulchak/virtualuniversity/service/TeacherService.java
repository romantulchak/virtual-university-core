package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.payload.request.ResetPasswordRequest;

public interface TeacherService{
    void create(Teacher teacher);
    void resetPassword(ResetPasswordRequest resetPasswordRequest);
}
