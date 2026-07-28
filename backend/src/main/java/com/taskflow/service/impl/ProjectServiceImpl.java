package com.taskflow.service.impl;

import com.taskflow.dto.Project.ProjectRequest;
import com.taskflow.dto.Project.ProjectResponse;
import com.taskflow.entity.Project;
import com.taskflow.entity.User;
import com.taskflow.repository.ProjectRepository;
import com.taskflow.service.ProjectService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectResponse getProjectById(Long projectId) {

        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();

            ProjectResponse projectResponse = new ProjectResponse();
            projectResponse.setProjectId(project.getId());
            projectResponse.setProjectName(project.getProjectName());
            projectResponse.setDescription(project.getDescription());
            projectResponse.setProjectManager(project.getProjectManager().getEmployeeId());
            projectResponse.setStartDate(project.getStartDate());
            projectResponse.setEndDate(project.getEndDate());

            return projectResponse;
        }
        return null;
    }

    public List<ProjectResponse> getAllProjects() {

        List<Project> projects = projectRepository.findAll();
//        return projects.stream().map(project -> {
//            ProjectResponse response = new ProjectResponse();
//            response.setProjectId(project.getId());
//            response.setProjectName(project.getProjectName());
//            response.setDescription(project.getDescription());
//            response.setProjectManager(project.getProjectManager().getEmployeeId());
//            response.setStartDate(project.getStartDate());
//            response.setEndDate(project.getEndDate());
//            return response;
//        }).collect(java.util.stream.Collectors.toList());

        return projects.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    private ProjectResponse convertToResponse(Project project) {
        ProjectResponse response = new ProjectResponse();
        response.setProjectId(project.getId());
        response.setProjectName(project.getProjectName());
        response.setDescription(project.getDescription());
        if (project.getProjectManager() != null) {
            response.setProjectManager(project.getProjectManager().getEmployeeId());
        }
        response.setStartDate(project.getStartDate());
        response.setEndDate(project.getEndDate());
        return response;
    }

    @Override
    public ProjectResponse createProject(ProjectRequest projectRequest) {

        Optional<Project> projectOptional = projectRepository.findById(projectRequest.getProjectId());
//        Optional<User> projectManagerOptional = userRepository.find

        if (projectOptional.isEmpty()) {
            Project project = new Project();
            project.setProjectName(projectRequest.getProjectName());
            project.setDescription(projectRequest.getDescription());
            project.setStartDate(projectRequest.getStartDate());
            project.setEndDate(projectRequest.getEndDate());
            project.setProjectManager(null); // Set the project manager if provided
            // Set the project manager if provided
            // You may need to fetch the User entity based on the employeeId
            // and set it to the project.setProjectManager(user);

            Project savedProject = projectRepository.save(project);

            return convertToResponse(savedProject);
        }
        return null;
    }
}
