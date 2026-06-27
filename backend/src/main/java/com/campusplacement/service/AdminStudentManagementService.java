package com.campusplacement.service;

import com.campusplacement.dto.response.StudentManagementResponse;

import java.util.List;

public interface AdminStudentManagementService {

    List<StudentManagementResponse> getAllStudents();

    StudentManagementResponse getStudentById(
            Long studentId
    );

    void activateStudent(
            Long studentId
    );

    void deactivateStudent(
            Long studentId
    );
}