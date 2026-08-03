package com.taskflow.enums;

import java.util.Optional;
import java.util.Locale;

public enum ProjectStatus {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED,
    ON_HOLD,
    CANCELLED;

    /**
     * Safely converts a string value to a ProjectStatus enum.
     * Handles null values and performs case-insensitive matching.
     *
     * @param value the string value to convert
     * @return Optional containing the ProjectStatus if valid, empty Optional otherwise
     */
    public static Optional<ProjectStatus> from(String value) {
        if (value == null || value.trim().isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(ProjectStatus.valueOf(value.trim().toUpperCase(Locale.ROOT)));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }

    /**
     * Converts a string value to a ProjectStatus enum, throwing an exception if invalid.
     *
     * @param value the string value to convert
     * @return the ProjectStatus enum value
     * @throws IllegalArgumentException if the value is not a valid ProjectStatus
     */
    public static ProjectStatus fromOrThrow(String value) {
        return from(value).orElseThrow(() ->
                new IllegalArgumentException("Invalid project status: " + value));
    }
}



