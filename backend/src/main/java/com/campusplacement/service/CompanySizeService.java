package com.campusplacement.service;

import com.campusplacement.dto.response.CompanySizeResponse;

import java.util.List;

public interface CompanySizeService {

    List<CompanySizeResponse> getAllCompanySizes();
}