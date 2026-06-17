package com.campusplacement.service.impl;

import com.campusplacement.dto.request.ProjectRequest;
import com.campusplacement.dto.response.ProjectResponse;
import com.campusplacement.entity.Project;
import com.campusplacement.entity.Student;
import com.campusplacement.entity.User;
import com.campusplacement.repository.ProjectRepository;
import com.campusplacement.repository.StudentRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.ProfileCompletionService;
import com.campusplacement.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final ProfileCompletionService profileCompletionService;

    @Override
    public ProjectResponse addProject(ProjectRequest request) {

        Student student = resolveCurrentStudent();

        validateUrls(request);

        String projectName = request.getProjectName().trim();
        String description = request.getDescription().trim();
        String techStack = request.getTechStack().trim();

        Project project = Project.builder()
                .student(student)
                .projectName(projectName)
                .description(description)
                .techStack(techStack)
                .githubUrl(request.getGithubUrl())
                .liveDemoUrl(request.getLiveDemoUrl())
                .build();

        Project savedProject = projectRepository.save(project);

        profileCompletionService.updateCompletion(student);

        return toResponse(savedProject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getMyProjects() {

        Student student = resolveCurrentStudent();

        return projectRepository.findByStudent(student)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ProjectResponse updateProject(Long projectId, ProjectRequest request) {

        Student student = resolveCurrentStudent();

        Project project = resolveOwnedProject(projectId, student);

        validateUrls(request);

        String projectName = request.getProjectName().trim();
        String description = request.getDescription().trim();
        String techStack = request.getTechStack().trim();

        project.setProjectName(projectName);
        project.setDescription(description);
        project.setTechStack(techStack);
        project.setGithubUrl(request.getGithubUrl());
        project.setLiveDemoUrl(request.getLiveDemoUrl());

        Project updatedProject = projectRepository.save(project);

        profileCompletionService.updateCompletion(student);

        return toResponse(updatedProject);
    }

    @Override
    public void deleteProject(Long projectId) {

        Student student = resolveCurrentStudent();

        Project project = resolveOwnedProject(projectId, student);

        projectRepository.delete(project);

        profileCompletionService.updateCompletion(student);
    }

    private Student resolveCurrentStudent() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException(
                    "No authenticated user found in security context."
            );
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Authenticated user not found: " + email
                        )
                );

        return studentRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student profile not found. Please create your profile first."
                        )
                );
    }

    private Project resolveOwnedProject(Long projectId, Student student) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Project not found with id: " + projectId
                        )
                );

        if (!project.getStudent().getId().equals(student.getId())) {
            throw new RuntimeException(
                    "You do not have permission to modify this project."
            );
        }

        return project;
    }

    private void validateUrls(ProjectRequest request) {

        validateUrl(request.getGithubUrl(), "GitHub URL");
        validateUrl(request.getLiveDemoUrl(), "Live demo URL");
    }

    private void validateUrl(String url, String fieldName) {

        if (url == null || url.isBlank()) {
            return;
        }

        if (!url.startsWith("https://")) {
            throw new IllegalArgumentException(
                    fieldName + " must start with https://"
            );
        }
    }

    private ProjectResponse toResponse(Project project) {

        return ProjectResponse.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .description(project.getDescription())
                .techStack(project.getTechStack())
                .githubUrl(project.getGithubUrl())
                .liveDemoUrl(project.getLiveDemoUrl())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}