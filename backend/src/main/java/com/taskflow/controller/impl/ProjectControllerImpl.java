package com.taskflow.controller.impl;

import com.taskflow.dto.Project.ProjectRequest;
import com.taskflow.dto.Project.ProjectResponse;
import com.taskflow.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/projects")
@RestController
public class ProjectControllerImpl {

    private final ProjectService projectService;

    public ProjectControllerImpl(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{projectId}")
    public ProjectResponse getProjectById(@PathVariable Long projectId) {
        return projectService.getProjectById(projectId);
    }

    @GetMapping("")
    public List<ProjectResponse> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping()
    public ProjectResponse createProject(@RequestBody ProjectRequest projectRequest) {
        return projectService.createProject(projectRequest);
    }

    @PutMapping("/{projectId}")
    public ProjectResponse updateProject(@PathVariable Long projectId, @RequestBody ProjectRequest projectRequest) {
        return projectService.updateProject(projectId, projectRequest);
    }

    @DeleteMapping("/{projectId}")
    public void deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
    }
}
