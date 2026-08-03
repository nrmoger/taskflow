package com.taskflow.dto.Project;

import com.taskflow.enums.ProjectStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {

    @NotNull(message = "Project ID cannot be null")
    @Size(min = 1, max = 20)
    private Long projectId;

    @NotNull(message = "Project name cannot be null")
    @Size(min = 3, max = 100, message = "Project name must be between 3 and 100 characters")
    private String projectName;

    @NotNull(message = "Project description cannot be null")
    @Size(min = 3, max = 255, message = "Project description must be between 3 and 255 characters")
    private String description;

    @NotNull(message = "Project manager ID cannot be null")
    private Long projectManagerId;

    @NotNull(message = "Project status cannot be null")
    @Size(min = 3, max = 50, message = "Project status must be between 3 and 50 characters")
    private ProjectStatus status;

    @NotNull(message = "Project start date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Project end date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

}
