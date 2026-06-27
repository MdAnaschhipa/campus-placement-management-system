package com.campusplacement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobDiscoveryResponse {

    private Long jobId;

    private String jobTitle;

    private String companyName;

    private String location;

    private String jobType;

    private Double salaryPackage;

    private Double minimumCgpa;

    private LocalDate applicationDeadline;

    private Integer numberOfRounds;
}