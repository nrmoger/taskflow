package com.taskflow.repository;

import com.taskflow.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByName(String name);
    Optional<Project> findByProjectId(String projectId);
}
