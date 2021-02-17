package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.payload.request.LoginRequest;
import com.romantulchak.virtualuniversity.payload.response.JwtResponse;
import com.romantulchak.virtualuniversity.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        return null;
    }
}
