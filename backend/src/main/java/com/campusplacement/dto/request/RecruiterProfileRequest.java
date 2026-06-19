package com.campusplacement.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruiterProfileRequest {

    private String companyName;

    private Long industryId;

    private Long companySizeId;

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
}