**REST APIs:**

Authentication:
- POST /api/auth/register
- POST /api/auth/login

Projects:
- GET /api/projects
- POST /api/projects
- GET /api/projects/{projectId}
- PUT /api/projects/{projectId}
- DELETE /api/projects/{projectId}

Tasks:
- GET /api/projects/tasks
- POST /api/projects/tasks
- GET /api/projects/{projectId}/tasks/{taskId}
- PUT /api/projects/{projectId}/tasks/{taskId}
- DELETE /api/projects/{projectId}/tasks/{taskId}

Comments:
- GET /api/projects/{projectId}/tasks/{taskId}/comments
- POST /api/projects/{projectId}/tasks/{taskId}/comments

Dashboard:
- GET /api/dashboard/summary
