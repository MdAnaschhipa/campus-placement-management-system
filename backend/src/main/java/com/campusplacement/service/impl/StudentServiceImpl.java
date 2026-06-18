package com.campusplacement.service.impl;

import com.campusplacement.dto.request.StudentProfileRequest;
import com.campusplacement.dto.response.StudentProfileResponse;
import com.campusplacement.entity.Branch;
import com.campusplacement.entity.Student;
import com.campusplacement.entity.User;
import com.campusplacement.repository.BranchRepository;
import com.campusplacement.repository.StudentRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.ProfileCompletionService;
import com.campusplacement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final ProfileCompletionService profileCompletionService;

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
                .branch(getBranch(request.getBranchId()))
                .semester(request.getSemester())
                .cgpa(request.getCgpa())
                .backlogs(request.getBacklogs())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .address(request.getAddress())
                .profilePhotoUrl(request.getProfilePhotoUrl())
                .linkedinUrl(request.getLinkedinUrl())
                .githubUrl(request.getGithubUrl())
                .resumeUrl(request.getResumeUrl())
                .build();

        Student savedStudent = studentRepository.save(student);

        savedStudent = profileCompletionService.updateCompletion(savedStudent);

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

        if (request.getBranchId() != null) {
            student.setBranch(getBranch(request.getBranchId()));
        }

        if (request.getSemester() != null) {
            student.setSemester(request.getSemester());
        }

        if (request.getCgpa() != null) {
            student.setCgpa(request.getCgpa());
        }

        if (request.getBacklogs() != null) {
            student.setBacklogs(request.getBacklogs());
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

        if (request.getLinkedinUrl() != null) {
            student.setLinkedinUrl(request.getLinkedinUrl());
        }

        if (request.getGithubUrl() != null) {
            student.setGithubUrl(request.getGithubUrl());
        }

        if (request.getResumeUrl() != null) {
            student.setResumeUrl(request.getResumeUrl());
        }

        student = profileCompletionService.updateCompletion(student);

        return mapToResponse(student);
    }

    private Branch getBranch(Long branchId) {

        return branchRepository.findById(branchId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Branch not found with id: " + branchId
                        )
                );
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

    private StudentProfileResponse mapToResponse(Student student) {

        return StudentProfileResponse.builder()
                .id(student.getUser().getId())
                .fullName(student.getUser().getFullName())
                .email(student.getUser().getEmail())
                .enrollmentNumber(student.getEnrollmentNumber())
                .branchId(student.getBranch().getId())
                .branchName(student.getBranch().getBranchName())
                .semester(student.getSemester())
                .cgpa(student.getCgpa())
                .backlogs(student.getBacklogs())
                .phoneNumber(student.getPhoneNumber())
                .dateOfBirth(student.getDateOfBirth())
                .gender(student.getGender())
                .address(student.getAddress())
                .profilePhotoUrl(student.getProfilePhotoUrl())
                .linkedinUrl(student.getLinkedinUrl())
                .githubUrl(student.getGithubUrl())
                .resumeUrl(student.getResumeUrl())
                .profileCompletionPercentage(student.getProfileCompletionPercentage())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}