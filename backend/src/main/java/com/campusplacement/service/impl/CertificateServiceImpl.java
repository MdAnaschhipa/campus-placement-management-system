package com.campusplacement.service.impl;

import com.campusplacement.dto.request.CertificateRequest;
import com.campusplacement.dto.response.CertificateResponse;
import com.campusplacement.entity.Certificate;
import com.campusplacement.entity.Student;
import com.campusplacement.entity.User;
import com.campusplacement.repository.CertificateRepository;
import com.campusplacement.repository.StudentRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @Override
    public CertificateResponse addCertificate(CertificateRequest request) {

        Student student = resolveCurrentStudent();

        Certificate certificate = Certificate.builder()
                .student(student)
                .certificateName(request.getCertificateName().trim())
                .description(request.getDescription().trim())
                .build();

        Certificate savedCertificate =
                certificateRepository.save(certificate);

        return toResponse(savedCertificate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateResponse> getMyCertificates() {

        Student student = resolveCurrentStudent();

        return certificateRepository.findByStudent(student)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CertificateResponse updateCertificate(
            Long certificateId,
            CertificateRequest request) {

        Student student = resolveCurrentStudent();

        Certificate certificate =
                resolveOwnedCertificate(
                        certificateId,
                        student
                );

        certificate.setCertificateName(
                request.getCertificateName().trim()
        );

        certificate.setDescription(
                request.getDescription().trim()
        );

        Certificate updatedCertificate =
                certificateRepository.save(certificate);

        return toResponse(updatedCertificate);
    }

    @Override
    public void deleteCertificate(Long certificateId) {

        Student student = resolveCurrentStudent();

        Certificate certificate =
                resolveOwnedCertificate(
                        certificateId,
                        student
                );

        certificateRepository.delete(certificate);
    }

    private Student resolveCurrentStudent() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new RuntimeException(
                    "No authenticated user found in security context."
            );
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Authenticated user not found: " + email
                        )
                );

        return studentRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student profile not found. Please create your profile first."
                        )
                );
    }

    private Certificate resolveOwnedCertificate(
            Long certificateId,
            Student student) {

        Certificate certificate =
                certificateRepository.findById(certificateId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Certificate not found with id: "
                                                + certificateId
                                )
                        );

        if (!certificate.getStudent()
                .getId()
                .equals(student.getId())) {

            throw new RuntimeException(
                    "You do not have permission to modify this certificate."
            );
        }

        return certificate;
    }

    private CertificateResponse toResponse(
            Certificate certificate) {

        return CertificateResponse.builder()
                .id(certificate.getId())
                .certificateName(
                        certificate.getCertificateName()
                )
                .description(
                        certificate.getDescription()
                )
                .createdAt(
                        certificate.getCreatedAt()
                )
                .updatedAt(
                        certificate.getUpdatedAt()
                )
                .build();
    }
}