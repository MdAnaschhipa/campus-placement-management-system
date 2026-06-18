package com.campusplacement.dto.response;

import com.campusplacement.enums.Gender;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfileResponse {

    private Long id;

    private String fullName;

    private String email;

    private String enrollmentNumber;

    private Long branchId;

    private String branchName;

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

    private Integer profileCompletionPercentage;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}