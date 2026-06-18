package com.campusplacement.controller;

import com.campusplacement.dto.response.CompanySizeResponse;
import com.campusplacement.service.CompanySizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company-sizes")
public class CompanySizeController {

    private final CompanySizeService companySizeService;

    @GetMapping
    public ResponseEntity<List<CompanySizeResponse>> getAllCompanySizes() {

        return ResponseEntity.ok(
                companySizeService.getAllCompanySizes()
        );
    }
}