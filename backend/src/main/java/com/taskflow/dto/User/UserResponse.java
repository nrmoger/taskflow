package com.taskflow.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}
