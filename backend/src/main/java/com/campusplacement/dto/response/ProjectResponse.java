package com.campusplacement.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {

    private Long id;
    private String projectName;
    private String description;
    private String techStack;
    private String githubUrl;
    private String liveDemoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}