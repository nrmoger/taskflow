package com.taskflow.entity;

import com.taskflow.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Employee ID cannot be blank")
    @Size(min = 3, max = 20)
    private String employeeId;

    @Column(nullable = false)
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50)
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 50)
    private String lastName;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 100)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    @NotNull(message = "Role cannot be null")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Status cannot be null")
    private UserStatus status;
}
