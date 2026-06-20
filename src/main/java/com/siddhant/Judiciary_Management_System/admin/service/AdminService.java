package com.siddhant.Judiciary_Management_System.admin.service;

import com.siddhant.Judiciary_Management_System.admin.dto.CreateUserRequest;
import com.siddhant.Judiciary_Management_System.user.entity.User;
import com.siddhant.Judiciary_Management_System.user.enums.Role;
import com.siddhant.Judiciary_Management_System.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public String createUser(CreateUserRequest request) {

        if(userRepository.existsByEmail(
                request.getEmail())) {

            throw new RuntimeException(
                    "Email already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return "User Created Successfully";
    }

    public List<User> getAllJudges() {
        return userRepository.findByRole(Role.JUDGE);
    }
}

