package com.taskflow.service.impl;

import com.taskflow.enums.ProjectStatus;
import java.util.Optional;

/**
 * Example: How to use ProjectStatus.from() and fromOrThrow() in your ServiceImpl
 *
 * This file demonstrates practical usage patterns for the enum parsing methods.
 */
public class ProjectStatusValidationExamples {

    /**
     * Example 1: Using from() with Optional - Safe approach
     * Use this when you want to gracefully handle invalid statuses
     */
    public void exampleWithOptional(String statusString) {
        Optional<ProjectStatus> status = ProjectStatus.from(statusString);

        if (status.isPresent()) {
            ProjectStatus validStatus = status.get();
            System.out.println("Valid project status: " + validStatus);
            // Use validStatus to update project
        } else {
            System.out.println("Invalid project status provided: " + statusString);
            System.out.println("Valid statuses are: NOT_STARTED, IN_PROGRESS, COMPLETED, ON_HOLD, CANCELLED");
            // Could throw a BadRequestException or ValidationException here
        }
    }

    /**
     * Example 2: Using fromOrThrow() - Fast fail approach
     * Use this when you expect the value to be valid and want to fail quickly on invalid input
     */
    public void exampleWithFromOrThrow(String statusString) {
        try {
            ProjectStatus validStatus = ProjectStatus.fromOrThrow(statusString);
            System.out.println("Valid project status: " + validStatus);
            // Use validStatus to update project
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            // Handle the exception - log it, return error response, etc.
        }
    }

    /**
     * Example 3: Using in validation method
     * A dedicated validation method you can call before processing requests
     */
    public boolean isValidProjectStatus(String status) {
        return ProjectStatus.from(status).isPresent();
    }

    /**
     * Example 4: Case-insensitive matching
     * The from() method automatically handles different cases
     */
    public void exampleCaseInsensitive() {
        // All of these will work:
        System.out.println(ProjectStatus.from("in_progress"));      // Optional[IN_PROGRESS]
        System.out.println(ProjectStatus.from("IN_PROGRESS"));      // Optional[IN_PROGRESS]
        System.out.println(ProjectStatus.from("In_Progress"));      // Optional[IN_PROGRESS]
        System.out.println(ProjectStatus.from("InProgress"));       // Optional.empty
        System.out.println(ProjectStatus.from("  IN_PROGRESS  ")); // Optional[IN_PROGRESS] (trimmed)
    }

    /**
     * Example 5: Null and empty value handling
     * The from() method safely handles null and empty strings
     */
    public void exampleNullHandling() {
        System.out.println(ProjectStatus.from(null));       // Optional.empty
        System.out.println(ProjectStatus.from(""));         // Optional.empty
        System.out.println(ProjectStatus.from("   "));      // Optional.empty
    }

    /**
     * Example 6: Real-world usage in validation logic
     * A practical example you might use in your ProjectServiceImpl
     */
    public ProjectStatus validateAndParseStatus(String statusFromRequest) throws IllegalArgumentException {
        // Approach A: Using Optional
        return ProjectStatus.from(statusFromRequest)
                .orElseThrow(() -> new IllegalArgumentException(
                    String.format("Invalid project status: '%s'. Valid values are: %s",
                        statusFromRequest,
                        "NOT_STARTED, IN_PROGRESS, COMPLETED, ON_HOLD, CANCELLED")
                ));
    }

    /**
     * Example 7: Real-world usage - More defensive approach
     */
    public ProjectStatus validateAndParseStatusDefensive(String statusFromRequest) {
        if (statusFromRequest == null || statusFromRequest.trim().isEmpty()) {
            throw new IllegalArgumentException("Project status cannot be null or empty");
        }

        return ProjectStatus.from(statusFromRequest)
                .orElseThrow(() -> new IllegalArgumentException(
                    String.format("Unknown project status: '%s'", statusFromRequest.trim())
                ));
    }

    /**
     * Example 8: Checking if current status can transition to new status
     * (Advanced: Only if you implement business logic for status transitions)
     */
    public boolean canTransitionStatus(ProjectStatus currentStatus, String requestedStatusString) {
        Optional<ProjectStatus> requestedStatus = ProjectStatus.from(requestedStatusString);

        if (requestedStatus.isEmpty()) {
            return false;
        }

        // Example transition rules (customize as needed):
        return switch (currentStatus) {
            case NOT_STARTED -> requestedStatus.get() == ProjectStatus.IN_PROGRESS;
            case IN_PROGRESS -> requestedStatus.get() == ProjectStatus.COMPLETED ||
                               requestedStatus.get() == ProjectStatus.ON_HOLD;
            case ON_HOLD -> requestedStatus.get() == ProjectStatus.IN_PROGRESS ||
                           requestedStatus.get() == ProjectStatus.CANCELLED;
            case COMPLETED -> false; // Cannot transition from completed
            case CANCELLED -> false; // Cannot transition from cancelled
        };
    }
}

