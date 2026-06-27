package com.campusplacement.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileCompletionResponse {

    private Integer completionPercentage;

    private Boolean personalDetailsCompleted;

    private Boolean academicDetailsCompleted;

    private Boolean skillsCompleted;

    private Boolean projectsCompleted;

    private Boolean resumeCompleted;
}