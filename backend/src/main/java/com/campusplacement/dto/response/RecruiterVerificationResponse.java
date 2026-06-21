package com.campusplacement.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruiterVerificationResponse {

    private Long recruiterId;

    private String companyName;

    private String officialCompanyEmail;

    private String gstNumber;

    private String registrationCertificateUrl;

    private String verificationStatus;

    private LocalDateTime updatedAt;
}