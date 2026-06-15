package com.campusplacement.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfileRequest {

    private String enrollmentNumber;
    private String branch;
    private Integer semester;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String profilePhotoUrl;
    private String resumeUrl;
}