package com.campusplacement.service;

import com.campusplacement.dto.request.StudentProfileRequest;
import com.campusplacement.dto.response.StudentProfileResponse;

public interface StudentService {

    StudentProfileResponse getProfile();

    StudentProfileResponse createProfile(StudentProfileRequest request);

    StudentProfileResponse updateProfile(StudentProfileRequest request);
}