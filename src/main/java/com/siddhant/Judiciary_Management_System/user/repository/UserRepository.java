package com.siddhant.Judiciary_Management_System.user.repository;

import com.siddhant.Judiciary_Management_System.user.entity.User;
import com.siddhant.Judiciary_Management_System.user.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

   Optional<User>findByEmail(String email);
    boolean existsByEmail(String email);
    long countByRole(Role role);

    List<User> findByRole(Role role);
}
