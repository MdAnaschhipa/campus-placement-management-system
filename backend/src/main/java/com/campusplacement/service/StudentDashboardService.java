package com.campusplacement.service;

import com.campusplacement.dto.response.StudentDashboardResponse;

public interface StudentDashboardService {

    StudentDashboardResponse getDashboard(Long studentId);
}