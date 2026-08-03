package com.taskflow.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    // All fields are optional for partial updates; do not use @NotBlank here.
    @Size(min = 3, max = 20, message = "Employee ID must be between 3 and 20 characters")
    private Long employeeId;

    @Size(min = 3, max = 20, message = "Employee Code must be between 3 and 20 characters")
    private String employeeCode;

    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;

    private String role;
}

