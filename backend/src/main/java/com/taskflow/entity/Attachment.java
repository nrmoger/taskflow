package com.taskflow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="attachments")
@Getter
@Setter
@NoArgsConstructor
public class Attachment {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Filename cannot be blank")
    @Size(min = 3, max = 255, message = "Filename must be between 3 and 255 characters")
    private String filename;

    @ManyToOne()
    @JoinColumn(name = "task_id")
    @NotNull(message = "Task cannot be null")
    private Task task;
}
