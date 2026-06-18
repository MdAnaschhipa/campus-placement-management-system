package com.campusplacement.dto.request;

import com.campusplacement.enums.Gender;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfileRequest {

    private String enrollmentNumber;

    private Long branchId;

    private Integer semester;

    private BigDecimal cgpa;

    private Integer backlogs;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String address;

    private String profilePhotoUrl;

    private String linkedinUrl;

    private String githubUrl;

    private String resumeUrl;
}