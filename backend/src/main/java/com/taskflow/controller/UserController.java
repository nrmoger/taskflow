package com.taskflow.controller;

import com.taskflow.dto.User.CreateUserRequest;
import com.taskflow.dto.User.UpdateUserRequest;
import com.taskflow.dto.User.UserResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;

public interface UserController {
    @GetMapping("/api/users/{employeeId}")
    UserResponse getUserByEmployeeId(String employeeId);

    @GetMapping("/api/users")
    List<UserResponse> getAllUsers();

    @PostMapping("/api/users")
    UserResponse createUser(@Valid CreateUserRequest request);

    @PutMapping("/api/users/{employeeId}")
    UserResponse updateUserByEmployeeId(String employeeId, @Valid CreateUserRequest request);

    @PatchMapping("/api/users/{employeeId}")
    UserResponse patchUserByEmployeeId(String employeeId, UpdateUserRequest request);

    @DeleteMapping("/api/users/{employeeId}")
    void deleteUser(String employeeId);
}
