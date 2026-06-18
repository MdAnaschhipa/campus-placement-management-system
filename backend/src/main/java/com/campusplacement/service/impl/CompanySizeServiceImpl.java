package com.campusplacement.service.impl;

import com.campusplacement.dto.response.CompanySizeResponse;
import com.campusplacement.entity.CompanySize;
import com.campusplacement.repository.CompanySizeRepository;
import com.campusplacement.service.CompanySizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanySizeServiceImpl implements CompanySizeService {

    private final CompanySizeRepository companySizeRepository;

    @Override
    public List<CompanySizeResponse> getAllCompanySizes() {

        return companySizeRepository.findByIsActiveTrueOrderBySizeNameAsc()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private CompanySizeResponse mapToResponse(CompanySize companySize) {

        return CompanySizeResponse.builder()
                .id(companySize.getId())
                .sizeName(companySize.getSizeName())
                .build();
    }
}