package com.siddhant.Judiciary_Management_System.auth.service;

import com.siddhant.Judiciary_Management_System.auth.dto.AuthResponse;
import com.siddhant.Judiciary_Management_System.auth.dto.LoginRequest;
import com.siddhant.Judiciary_Management_System.auth.dto.RegisterRequest;
import com.siddhant.Judiciary_Management_System.security.jwt.JwtService;
import com.siddhant.Judiciary_Management_System.user.entity.User;
import com.siddhant.Judiciary_Management_System.user.enums.Role;
import com.siddhant.Judiciary_Management_System.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public String register(RegisterRequest request){

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CITIZEN)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return "User Registered Successfully";
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid Credentials");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(token,user.getRole().name(),user.getEmail());
    }
}
