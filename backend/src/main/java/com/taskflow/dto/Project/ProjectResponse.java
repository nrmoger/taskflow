package com.taskflow.dto.Project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse {

    private Long projectId;
    private String projectName;
    private String description;
    private String status;
    private String projectManager;
    private LocalDate startDate;
    private LocalDate endDate;
}