package com.campusplacement.service.impl;

import com.campusplacement.dto.response.IndustryResponse;
import com.campusplacement.entity.Industry;
import com.campusplacement.repository.IndustryRepository;
import com.campusplacement.service.IndustryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IndustryServiceImpl implements IndustryService {

    private final IndustryRepository industryRepository;

    @Override
    public List<IndustryResponse> getAllIndustries() {

        return industryRepository.findByIsActiveTrueOrderByIndustryNameAsc()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private IndustryResponse mapToResponse(Industry industry) {

        return IndustryResponse.builder()
                .id(industry.getId())
                .industryName(industry.getIndustryName())
                .build();
    }
}