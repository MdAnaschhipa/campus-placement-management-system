package com.campusplacement.controller;

import com.campusplacement.dto.request.SkillRequest;
import com.campusplacement.dto.response.SkillResponse;
import com.campusplacement.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/skills")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<SkillResponse> addSkill(
            @Valid @RequestBody SkillRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(skillService.addSkill(request));
    }

    @GetMapping
    public ResponseEntity<List<SkillResponse>> getMySkills() {

        return ResponseEntity.ok(
                skillService.getMySkills()
        );
    }

    @PutMapping("/{skillId}")
    public ResponseEntity<SkillResponse> updateSkill(
            @PathVariable Long skillId,
            @Valid @RequestBody SkillRequest request) {

        return ResponseEntity.ok(
                skillService.updateSkill(skillId, request)
        );
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> deleteSkill(
            @PathVariable Long skillId) {

        skillService.deleteSkill(skillId);

        return ResponseEntity.noContent().build();
    }
}