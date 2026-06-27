package com.campusplacement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobModerationResponse {

    private Long jobId;

    private String jobTitle;

    private String companyName;

    private String jobType;

    private String location;

    private Double salaryPackage;

    private String jobStatus;

    private Integer totalApplications;

    private LocalDate applicationDeadline;

    private LocalDateTime createdAt;
}