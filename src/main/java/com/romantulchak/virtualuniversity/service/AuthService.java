package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.payload.request.LoginRequest;
import com.romantulchak.virtualuniversity.payload.request.ResetPasswordRequest;
import com.romantulchak.virtualuniversity.payload.response.JwtResponse;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest);
}
