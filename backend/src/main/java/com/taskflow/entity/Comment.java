package com.taskflow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Comment message cannot be blank")
    @Size(min = 1, max = 1000, message = "Comment must be between 1 and 1000 characters")
    private String message;

    @ManyToOne()
    @JoinColumn(name="task_id")
    @NotNull(message = "Task cannot be null")
    private Task task;
}
