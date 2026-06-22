package com.campusplacement.dto.request;

import com.campusplacement.enums.JobType;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequest {

    private String jobTitle;

    private String jobDescription;

    private String location;

    private JobType jobType;

    private Double salaryPackage;

    private Double minimumCgpa;

    private LocalDate applicationDeadline;

    private Integer numberOfRounds;

    private Set<Long> eligibleBranchIds;

    private Set<Long> requiredSkillIds;

    private Set<Integer> eligibleSemesters;
}