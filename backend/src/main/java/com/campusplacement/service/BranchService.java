package com.campusplacement.service;

import com.campusplacement.dto.response.BranchResponse;

import java.util.List;

public interface BranchService {

    List<BranchResponse> getAllBranches();
}