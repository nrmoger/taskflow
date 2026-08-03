package com.taskflow.controller;

import com.taskflow.dto.Project.ProjectResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ProjectController {

    @GetMapping("/api/projects/{projectId}")
    ProjectResponse getProjectById(@PathVariable Long projectId);

    @GetMapping("/api/projects")
    List<ProjectResponse> getAllProjects();

    @PostMapping("/api/projects")
    ProjectResponse createProject(@Valid ProjectResponse projectResponse);

    @PutMapping("/api/projects/{projectId}")
    ProjectResponse updateProject(@PathVariable Long projectId, @Valid ProjectResponse projectResponse);

    @DeleteMapping("/api/projects/{projectId}")
    void deleteProject(@PathVariable Long projectId);
}
