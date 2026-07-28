package com.taskflow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="tasks")
@Getter
@Setter
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @Column
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @Column(nullable = false)
    @NotBlank(message = "Status cannot be blank")
    private String status;

    @ManyToOne()
    @JoinColumn(name="project_id")
    @NotNull(message = "Project cannot be null")
    private Project project;

    @ManyToOne()
    @JoinColumn(name="developer_id")
    private User developer;

    @ManyToOne()
    @JoinColumn(name="qa_id")
    private User qa;

    @OneToMany(mappedBy = "task")
    private List<Comment> comments;

    @OneToMany(mappedBy = "task")
    private List<Attachment> attachments;
}
