package com.campusplacement.service;

import com.campusplacement.dto.request.SkillRequest;
import com.campusplacement.dto.response.SkillResponse;

import java.util.List;

public interface SkillService {

    SkillResponse addSkill(SkillRequest request);

    List<SkillResponse> getMySkills();

    SkillResponse updateSkill(Long skillId, SkillRequest request);

    void deleteSkill(Long skillId);
}