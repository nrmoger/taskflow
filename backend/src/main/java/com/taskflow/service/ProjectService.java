package com.taskflow.service;

import com.taskflow.dto.Project.ProjectRequest;
import com.taskflow.dto.Project.ProjectResponse;

import java.util.List;

public interface ProjectService {

    ProjectResponse getProjectById(Long projectId);

    List<ProjectResponse> getAllProjects();

    ProjectResponse createProject(ProjectRequest projectRequest);

    ProjectResponse updateProject(Long projectId, ProjectRequest projectRequest);

    void deleteProject(Long projectId);
}
