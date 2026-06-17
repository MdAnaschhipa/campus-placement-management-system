package com.campusplacement.service;

import com.campusplacement.dto.request.ProjectRequest;
import com.campusplacement.dto.response.ProjectResponse;

import java.util.List;

public interface ProjectService {

    ProjectResponse addProject(ProjectRequest request);

    List<ProjectResponse> getMyProjects();

    ProjectResponse updateProject(Long projectId, ProjectRequest request);

    void deleteProject(Long projectId);
}