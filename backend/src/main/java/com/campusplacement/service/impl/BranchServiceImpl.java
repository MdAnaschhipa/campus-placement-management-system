package com.campusplacement.service.impl;

import com.campusplacement.dto.response.BranchResponse;
import com.campusplacement.entity.Branch;
import com.campusplacement.repository.BranchRepository;
import com.campusplacement.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    @Override
    public List<BranchResponse> getAllBranches() {

        return branchRepository.findByIsActiveTrueOrderByBranchNameAsc()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private BranchResponse mapToResponse(Branch branch) {

        return BranchResponse.builder()
                .id(branch.getId())
                .branchCode(branch.getBranchCode())
                .branchName(branch.getBranchName())
                .build();
    }
}