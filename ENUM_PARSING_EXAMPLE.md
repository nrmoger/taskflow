# ProjectStatus Enum Parsing - Usage Examples

The `ProjectStatus` enum now includes two helpful static methods for safely converting strings to enum values:

## Methods Available

### 1. `from(String value)` - Safe Parsing (Returns Optional)
Returns an `Optional<ProjectStatus>` to safely handle invalid values.

**Usage in ServiceImpl:**
```java
import com.taskflow.enums.ProjectStatus;
import java.util.Optional;

public void validateProjectStatus(String statusString) {
    Optional<ProjectStatus> status = ProjectStatus.from(statusString);
    
    if (status.isPresent()) {
        // Valid status
        ProjectStatus validStatus = status.get();
        System.out.println("Valid status: " + validStatus);
    } else {
        // Invalid status - handle gracefully
        System.out.println("Invalid status provided: " + statusString);
        throw new IllegalArgumentException("Invalid project status: " + statusString);
    }
}
```

### 2. `fromOrThrow(String value)` - Strict Parsing (Throws Exception)
Directly throws an `IllegalArgumentException` if the value is invalid. Use this when you want fail-fast behavior.

**Usage in ServiceImpl:**
```java
public ProjectResponse createProject(ProjectRequest projectRequest) {
    Project project = new Project();
    project.setProjectName(projectRequest.getProjectName());
    project.setDescription(projectRequest.getDescription());
    
    // If status is a string, parse it safely
    // (Note: In the current DTO, status is already typed as ProjectStatus)
    try {
        ProjectStatus status = ProjectStatus.fromOrThrow(projectRequest.getStatus().toString());
        project.setStatus(status);
    } catch (IllegalArgumentException e) {
        throw new BadRequestException("Invalid project status: " + e.getMessage());
    }
    
    // ... rest of method
}
```

## How It Works

### Case-Insensitive Matching
Both methods support case-insensitive input:
- `ProjectStatus.from("in_progress")` → Returns `Optional.of(ProjectStatus.IN_PROGRESS)`
- `ProjectStatus.from("IN_PROGRESS")` → Returns `Optional.of(ProjectStatus.IN_PROGRESS)`
- `ProjectStatus.from("In_Progress")` → Returns `Optional.of(ProjectStatus.IN_PROGRESS)`

### Null & Empty Handling
- `ProjectStatus.from(null)` → Returns `Optional.empty()`
- `ProjectStatus.from("")` → Returns `Optional.empty()`
- `ProjectStatus.from("   ")` → Returns `Optional.empty()` (whitespace is trimmed)

### Invalid Values
- `ProjectStatus.from("INVALID_STATUS")` → Returns `Optional.empty()`
- `ProjectStatus.fromOrThrow("INVALID_STATUS")` → Throws `IllegalArgumentException`

## Valid ProjectStatus Values
- `NOT_STARTED`
- `IN_PROGRESS`
- `COMPLETED`
- `ON_HOLD`
- `CANCELLED`

## Integration with Jackson (JSON Deserialization)

Currently, the `ProjectRequest` DTO already has the status field typed as `ProjectStatus`, which means Jackson automatically handles the conversion during deserialization. If you need custom JSON deserialization logic, you can add a `@JsonCreator` annotation to the enum:

```java
@JsonCreator
public static ProjectStatus forValue(String value) {
    return fromOrThrow(value);
}
```

This would allow your API to be more flexible with invalid status values in requests, providing clear error messages instead of generic deserialization errors.

## Best Practices

1. **Use `from()` + `Optional`** when you want to check if a value is valid and handle the invalid case gracefully
2. **Use `fromOrThrow()`** when you expect all values to be valid and want fast failure
3. **Prefer Optionals** for cleaner null handling instead of null checks
4. **Validate early** - convert strings to enums at the controller/service boundary, not deep in business logic

