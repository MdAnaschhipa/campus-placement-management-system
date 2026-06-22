package com.campusplacement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationStatusResponse {

    private Long applicationId;

    private String studentName;

    private String jobTitle;

    private String applicationStatus;
}