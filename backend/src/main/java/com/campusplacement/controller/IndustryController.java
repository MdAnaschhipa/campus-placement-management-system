package com.campusplacement.controller;

import com.campusplacement.dto.response.IndustryResponse;
import com.campusplacement.service.IndustryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/industries")
@RequiredArgsConstructor
public class IndustryController {

    private final IndustryService industryService;

    @GetMapping
    public ResponseEntity<List<IndustryResponse>> getAllIndustries() {

        return ResponseEntity.ok(
                industryService.getAllIndustries()
        );
    }
}