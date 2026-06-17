package com.campusplacement.service;

import com.campusplacement.dto.request.CertificateRequest;
import com.campusplacement.dto.response.CertificateResponse;

import java.util.List;

public interface CertificateService {

    CertificateResponse addCertificate(CertificateRequest request);

    List<CertificateResponse> getMyCertificates();

    CertificateResponse updateCertificate(
            Long certificateId,
            CertificateRequest request
    );

    void deleteCertificate(Long certificateId);
}