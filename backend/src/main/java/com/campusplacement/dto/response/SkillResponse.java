package com.campusplacement.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillResponse {

    private Long id;
    private String skillName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}