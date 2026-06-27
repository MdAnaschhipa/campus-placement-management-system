package com.campusplacement.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruiterManagementResponse {

    private Long recruiterId;

    private Long userId;

    private String companyName;

    private String officialCompanyEmail;

    private String contactPersonName;

    private String verificationStatus;

    private Boolean active;

    private LocalDateTime createdAt;
}