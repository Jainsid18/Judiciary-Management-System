package com.siddhant.Judiciary_Management_System.auth.dto;

import com.siddhant.Judiciary_Management_System.user.enums.Role;
import lombok.Data;

@Data
public class RegisterRequest {

    private String fullName;
    private String email;
    private String password;
    private String phone;
    private Role role;
}
