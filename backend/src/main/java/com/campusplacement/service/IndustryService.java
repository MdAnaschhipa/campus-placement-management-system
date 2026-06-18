package com.campusplacement.service;

import com.campusplacement.dto.response.IndustryResponse;

import java.util.List;

public interface IndustryService {

    List<IndustryResponse> getAllIndustries();
}