package com.campusplacement.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruiterVerificationRequest {

    private String officialCompanyEmail;

    private String gstNumber;

    private String registrationCertificateUrl;
}