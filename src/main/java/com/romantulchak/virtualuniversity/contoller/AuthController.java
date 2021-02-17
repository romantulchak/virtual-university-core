package com.romantulchak.virtualuniversity.contoller;

import com.romantulchak.virtualuniversity.payload.request.LoginRequest;
import com.romantulchak.virtualuniversity.payload.response.JwtResponse;
import com.romantulchak.virtualuniversity.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    @Autowired
    public AuthController(AuthServiceImpl authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

}
