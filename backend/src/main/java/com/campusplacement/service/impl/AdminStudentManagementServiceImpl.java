package com.campusplacement.service.impl;

import com.campusplacement.dto.response.StudentManagementResponse;
import com.campusplacement.entity.Student;
import com.campusplacement.entity.User;
import com.campusplacement.enums.AuditAction;
import com.campusplacement.enums.AuditEntityType;
import com.campusplacement.repository.StudentRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.AdminStudentManagementService;
import com.campusplacement.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminStudentManagementServiceImpl
        implements AdminStudentManagementService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    @Override
    @Transactional(readOnly = true)
    public List<StudentManagementResponse> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentManagementResponse getStudentById(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        return mapToResponse(student);
    }

    @Override
    public void activateStudent(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        student.getUser().setActive(true);

        studentRepository.save(student);

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String adminEmail = authentication.getName();

        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() ->
                        new RuntimeException("Admin not found"));

        auditLogService.createLog(
                admin.getId(),
                admin.getEmail(),
                AuditAction.ACTIVATE_STUDENT,
                AuditEntityType.STUDENT,
                student.getId(),
                "Activated student: "
                        + student.getUser().getFullName()
        );
    }

    @Override
    public void deactivateStudent(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        student.getUser().setActive(false);

        studentRepository.save(student);

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String adminEmail = authentication.getName();

        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() ->
                        new RuntimeException("Admin not found"));

        auditLogService.createLog(
                admin.getId(),
                admin.getEmail(),
                AuditAction.DEACTIVATE_STUDENT,
                AuditEntityType.STUDENT,
                student.getId(),
                "Deactivated student: "
                        + student.getUser().getFullName()
        );
    }

    private StudentManagementResponse mapToResponse(Student student) {

        return StudentManagementResponse.builder()
                .studentId(student.getId())
                .userId(student.getUser().getId())
                .fullName(student.getUser().getFullName())
                .email(student.getUser().getEmail())
                .enrollmentNumber(student.getEnrollmentNumber())
                .branchName(student.getBranch() != null
                        ? student.getBranch().getBranchName()
                        : null)
                .semester(student.getSemester())
                .cgpa(student.getCgpa() != null
                        ? student.getCgpa().doubleValue()
                        : null)
                .active(student.getUser().isActive())
                .createdAt(student.getCreatedAt())
                .build();
    }
}