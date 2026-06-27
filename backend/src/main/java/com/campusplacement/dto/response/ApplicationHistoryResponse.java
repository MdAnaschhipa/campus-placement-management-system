package com.campusplacement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationHistoryResponse {

    private Long applicationId;

    private Long jobId;

    private String jobTitle;

    private String companyName;

    private String applicationStatus;

    private LocalDateTime appliedAt;
}