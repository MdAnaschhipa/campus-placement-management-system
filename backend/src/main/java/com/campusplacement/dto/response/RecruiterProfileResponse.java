package com.campusplacement.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruiterProfileResponse {

    private Long id;

    private String companyName;

    private Long industryId;
    private String industryName;

    private Long companySizeId;
    private String companySizeName;

    private String companyWebsite;

    private String companyDescription;

    private String headquarters;

    private String companyLogoUrl;

    private String contactPersonName;

    private String contactPersonDesignation;

    private String contactPhoneNumber;

    private String officialCompanyEmail;

    private String gstNumber;

    private String registrationCertificateUrl;

    private String verificationStatus;

    private Boolean isProfileCompleted;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}