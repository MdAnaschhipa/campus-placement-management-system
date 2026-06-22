package com.campusplacement.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobResponse {

    private Long id;

    private String jobTitle;

    private String jobDescription;

    private String location;

    private String jobType;

    private Double salaryPackage;

    private Double minimumCgpa;

    private LocalDate applicationDeadline;

    private Integer numberOfRounds;

    private String jobStatus;

    private Set<String> eligibleBranches;

    private Set<String> requiredSkills;

    private Set<Integer> eligibleSemesters;

    private LocalDateTime createdAt;
}