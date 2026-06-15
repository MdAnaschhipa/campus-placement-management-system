package com.campusplacement.dto.response;

import lombok.*;

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
    private String branch;
    private Integer semester;

    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;

    private String profilePhotoUrl;
    private String resumeUrl;

    private Integer profileCompletionPercentage;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}