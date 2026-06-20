package com.siddhant.Judiciary_Management_System.admin.dto;

import com.siddhant.Judiciary_Management_System.user.enums.Role;
import lombok.Data;

@Data
public class CreateUserRequest {

    private String fullName;
    private String email;
    private String password;
    private String phone;
    private Role role;
}
