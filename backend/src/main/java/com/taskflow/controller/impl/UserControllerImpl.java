package com.taskflow.controller.impl;

import com.taskflow.controller.UserController;
import com.taskflow.dto.User.CreateUserRequest;
import com.taskflow.dto.User.UserResponse;

import com.taskflow.dto.User.UpdateUserRequest;
import com.taskflow.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users/{employeeId}")
    public UserResponse getUserByEmployeeId(@PathVariable String employeeId) {
        return userService.getUserByEmployeeId(employeeId);
    }

    @GetMapping("/api/users")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/api/users")
    public UserResponse createUser(@RequestBody CreateUserRequest request) {

        return userService.createUser(request);
    }

    @PutMapping("/api/users/{employeeId}")
    public UserResponse updateUserByEmployeeId(@PathVariable String employeeId, @RequestBody CreateUserRequest request) {
        return userService.updateUserByEmployeeId(employeeId, request);
    }

    @PatchMapping("/api/users/{employeeId}")
    public UserResponse patchUserByEmployeeId(@PathVariable String employeeId, @RequestBody UpdateUserRequest request) {
        return userService.updateUserPartial(employeeId, request);
    }

    @DeleteMapping("/api/users/{employeeId}")
    public void deleteUser(@PathVariable String employeeId) {
        userService.deleteUser(employeeId);
    }
}
