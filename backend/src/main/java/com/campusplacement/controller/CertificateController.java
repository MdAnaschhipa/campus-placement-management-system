package com.campusplacement.controller;

import com.campusplacement.dto.request.CertificateRequest;
import com.campusplacement.dto.response.CertificateResponse;
import com.campusplacement.service.CertificateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/certificates")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class CertificateController {

    private final CertificateService certificateService;

    @PostMapping
    public ResponseEntity<CertificateResponse> addCertificate(
            @Valid @RequestBody CertificateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(certificateService.addCertificate(request));
    }

    @GetMapping
    public ResponseEntity<List<CertificateResponse>> getMyCertificates() {

        return ResponseEntity.ok(
                certificateService.getMyCertificates()
        );
    }

    @PutMapping("/{certificateId}")
    public ResponseEntity<CertificateResponse> updateCertificate(
            @PathVariable Long certificateId,
            @Valid @RequestBody CertificateRequest request) {

        return ResponseEntity.ok(
                certificateService.updateCertificate(
                        certificateId,
                        request
                )
        );
    }

    @DeleteMapping("/{certificateId}")
    public ResponseEntity<Void> deleteCertificate(
            @PathVariable Long certificateId) {

        certificateService.deleteCertificate(certificateId);

        return ResponseEntity.noContent().build();
    }
}