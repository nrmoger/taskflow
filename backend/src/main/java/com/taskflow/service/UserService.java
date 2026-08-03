package com.taskflow.service;

import com.taskflow.dto.User.CreateUserRequest;
import com.taskflow.dto.User.UpdateUserRequest;
import com.taskflow.dto.User.UserResponse;

import java.util.List;

public interface UserService {
    // Define service methods for user management
    UserResponse createUser(CreateUserRequest request);

    UserResponse getUserByEmployeeId(Long employeeId);

    List<UserResponse> getAllUsers();

    UserResponse updateUserByEmployeeId(Long employeeId, CreateUserRequest request);

    UserResponse updateUserPartial(Long employeeId, UpdateUserRequest request);

    void deleteUser(Long employeeId);
}
