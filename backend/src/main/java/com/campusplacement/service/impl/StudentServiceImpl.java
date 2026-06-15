package com.campusplacement.service.impl;

import org.springframework.security.core.Authentication;
import com.campusplacement.dto.request.StudentProfileRequest;
import com.campusplacement.dto.response.StudentProfileResponse;
import com.campusplacement.entity.Student;
import com.campusplacement.entity.User;
import com.campusplacement.repository.StudentRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public StudentProfileResponse getProfile() {
        User user = getCurrentUser();

        Student student = studentRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        return mapToResponse(student);
    }

    @Override
    public StudentProfileResponse createProfile(StudentProfileRequest request) {

        User user = getCurrentUser();
        if (request.getSemester() != null
                && (request.getSemester() < 1 || request.getSemester() > 8)) {
            throw new RuntimeException("Semester must be between 1 and 8");
        }

        if (studentRepository.existsByUser(user)) {
            throw new RuntimeException("Profile already exists");
        }

        if (request.getEnrollmentNumber() != null
                && studentRepository.existsByEnrollmentNumber(request.getEnrollmentNumber())) {
            throw new RuntimeException("Enrollment number already exists");
        }

        Student student = Student.builder()
                .user(user)
                .enrollmentNumber(request.getEnrollmentNumber())
                .branch(request.getBranch())
                .semester(request.getSemester())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .address(request.getAddress())
                .profilePhotoUrl(request.getProfilePhotoUrl())
                .resumeUrl(request.getResumeUrl())
                .build();

        student.setProfileCompletionPercentage(
                calculateProfileCompletionFromEntity(student)
        );

        Student savedStudent = studentRepository.save(student);

        return mapToResponse(savedStudent);
    }

    @Override
    public StudentProfileResponse updateProfile(StudentProfileRequest request) {

        User user = getCurrentUser();
        if (request.getSemester() != null
                && (request.getSemester() < 1 || request.getSemester() > 8)) {
            throw new RuntimeException("Semester must be between 1 and 8");
        }

        Student student = studentRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));


        if (request.getEnrollmentNumber() != null
                && !request.getEnrollmentNumber().equals(student.getEnrollmentNumber())
                && studentRepository.existsByEnrollmentNumber(request.getEnrollmentNumber())) {
            throw new RuntimeException("Enrollment number already exists");
        }

        if (request.getEnrollmentNumber() != null) {
            student.setEnrollmentNumber(request.getEnrollmentNumber());
        }

        if (request.getBranch() != null) {
            student.setBranch(request.getBranch());
        }

        if (request.getSemester() != null) {
            student.setSemester(request.getSemester());
        }

        if (request.getPhoneNumber() != null) {
            student.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getDateOfBirth() != null) {
            student.setDateOfBirth(request.getDateOfBirth());
        }

        if (request.getGender() != null) {
            student.setGender(request.getGender());
        }

        if (request.getAddress() != null) {
            student.setAddress(request.getAddress());
        }

        if (request.getProfilePhotoUrl() != null) {
            student.setProfilePhotoUrl(request.getProfilePhotoUrl());
        }

        if (request.getResumeUrl() != null) {
            student.setResumeUrl(request.getResumeUrl());
        }

        student.setProfileCompletionPercentage(
                calculateProfileCompletionFromEntity(student)
        );

        Student updatedStudent = studentRepository.save(student);

        return mapToResponse(updatedStudent);
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException(
                    "No authenticated user found in security context."
            );
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Authenticated user not found: " + email
                        )
                );
    }

    private int calculateProfileCompletionFromEntity(Student student) {

        int completed = 0;
        int total = 9;

        if (student.getEnrollmentNumber() != null && !student.getEnrollmentNumber().isBlank()) completed++;
        if (student.getBranch() != null && !student.getBranch().isBlank()) completed++;
        if (student.getSemester() != null) completed++;
        if (student.getPhoneNumber() != null && !student.getPhoneNumber().isBlank()) completed++;
        if (student.getDateOfBirth() != null) completed++;
        if (student.getGender() != null && !student.getGender().isBlank()) completed++;
        if (student.getAddress() != null && !student.getAddress().isBlank()) completed++;
        if (student.getProfilePhotoUrl() != null && !student.getProfilePhotoUrl().isBlank()) completed++;
        if (student.getResumeUrl() != null && !student.getResumeUrl().isBlank()) completed++;

        return (completed * 100) / total;
    }

    private StudentProfileResponse mapToResponse(Student student) {

        return StudentProfileResponse.builder()
                .id(student.getUser().getId())
                .fullName(student.getUser().getFullName())
                .email(student.getUser().getEmail())
                .enrollmentNumber(student.getEnrollmentNumber())
                .branch(student.getBranch())
                .semester(student.getSemester())
                .phoneNumber(student.getPhoneNumber())
                .dateOfBirth(student.getDateOfBirth())
                .gender(student.getGender())
                .address(student.getAddress())
                .profilePhotoUrl(student.getProfilePhotoUrl())
                .resumeUrl(student.getResumeUrl())
                .profileCompletionPercentage(student.getProfileCompletionPercentage())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}