package com.siddhant.Judiciary_Management_System.auth.controller;

import com.siddhant.Judiciary_Management_System.auth.dto.AuthResponse;
import com.siddhant.Judiciary_Management_System.auth.dto.LoginRequest;
import com.siddhant.Judiciary_Management_System.auth.dto.RegisterRequest;
import com.siddhant.Judiciary_Management_System.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}
