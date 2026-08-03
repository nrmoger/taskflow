package com.taskflow.entity;

import com.taskflow.enums.ProjectStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="projects")
@Getter
@Setter
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Project name cannot be blank")
    @Size(min = 3, max = 100, message = "Project name must be between 3 and 100 characters")
    private String projectName;

    @Column
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="project_manager_id")
    @NotNull(message = "Project manager cannot be null")
    private User projectManagerId;

    @OneToMany(mappedBy="project")
    private List<Task> tasks;
}
