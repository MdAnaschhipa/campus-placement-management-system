package com.campusplacement.service;

import com.campusplacement.dto.request.InterviewRequest;
import com.campusplacement.dto.response.InterviewResponse;

import java.util.List;

public interface InterviewService {

    InterviewResponse scheduleInterview(
            InterviewRequest request
    );

    List<InterviewResponse> getApplicationInterviews(
            Long applicationId
    );

    List<InterviewResponse> getRecruiterInterviews();
}