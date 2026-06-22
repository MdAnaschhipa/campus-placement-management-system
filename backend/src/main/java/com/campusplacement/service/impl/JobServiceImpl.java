package com.campusplacement.service.impl;

import com.campusplacement.dto.request.JobRequest;
import com.campusplacement.dto.response.JobResponse;
import com.campusplacement.entity.Branch;
import com.campusplacement.entity.Job;
import com.campusplacement.entity.Recruiter;
import com.campusplacement.entity.Skill;
import com.campusplacement.entity.User;
import com.campusplacement.enums.JobStatus;
import com.campusplacement.enums.Role;
import com.campusplacement.enums.VerificationStatus;
import com.campusplacement.repository.BranchRepository;
import com.campusplacement.repository.JobRepository;
import com.campusplacement.repository.RecruiterRepository;
import com.campusplacement.repository.SkillRepository;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final RecruiterRepository recruiterRepository;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final SkillRepository skillRepository;

    @Override
    public JobResponse createJob(JobRequest request) {

        User user = getCurrentUser();
        validateRecruiterRole(user);

        Recruiter recruiter = recruiterRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Recruiter profile not found"));

        if (recruiter.getVerificationStatus() != VerificationStatus.VERIFIED) {
            throw new RuntimeException("Recruiter is not verified. Cannot post jobs.");
        }

        Set<Branch> eligibleBranches = resolveBranches(request.getEligibleBranchIds());
        Set<Skill> requiredSkills = resolveSkills(request.getRequiredSkillIds());

        Job job = Job.builder()
                .recruiter(recruiter)
                .jobTitle(request.getJobTitle())
                .jobDescription(request.getJobDescription())
                .location(request.getLocation())
                .jobType(request.getJobType())
                .salaryPackage(request.getSalaryPackage())
                .minimumCgpa(request.getMinimumCgpa())
                .applicationDeadline(request.getApplicationDeadline())
                .numberOfRounds(request.getNumberOfRounds())
                .eligibleBranches(eligibleBranches)
                .requiredSkills(requiredSkills)
                .eligibleSemesters(
                        request.getEligibleSemesters() != null
                                ? request.getEligibleSemesters()
                                : new HashSet<>()
                )
                .build();

        Job savedJob = jobRepository.save(job);

        return mapToResponse(savedJob);
    }

    @Override
    public JobResponse updateJob(Long jobId, JobRequest request) {

        User user = getCurrentUser();
        validateRecruiterRole(user);

        Job job = resolveOwnedJob(jobId, user);

        if (request.getJobTitle() != null) {
            job.setJobTitle(request.getJobTitle());
        }

        if (request.getJobDescription() != null) {
            job.setJobDescription(request.getJobDescription());
        }

        if (request.getLocation() != null) {
            job.setLocation(request.getLocation());
        }

        if (request.getJobType() != null) {
            job.setJobType(request.getJobType());
        }

        if (request.getSalaryPackage() != null) {
            job.setSalaryPackage(request.getSalaryPackage());
        }

        if (request.getMinimumCgpa() != null) {
            job.setMinimumCgpa(request.getMinimumCgpa());
        }

        if (request.getApplicationDeadline() != null) {
            job.setApplicationDeadline(request.getApplicationDeadline());
        }

        if (request.getNumberOfRounds() != null) {
            job.setNumberOfRounds(request.getNumberOfRounds());
        }

        if (request.getEligibleBranchIds() != null) {
            job.setEligibleBranches(resolveBranches(request.getEligibleBranchIds()));
        }

        if (request.getRequiredSkillIds() != null) {
            job.setRequiredSkills(resolveSkills(request.getRequiredSkillIds()));
        }

        if (request.getEligibleSemesters() != null) {
            job.setEligibleSemesters(request.getEligibleSemesters());
        }

        Job updatedJob = jobRepository.save(job);

        return mapToResponse(updatedJob);
    }

    @Override
    public void deleteJob(Long jobId) {

        User user = getCurrentUser();
        validateRecruiterRole(user);

        Job job = resolveOwnedJob(jobId, user);

        jobRepository.delete(job);
    }

    @Override
    @Transactional(readOnly = true)
    public JobResponse getJobById(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));

        return mapToResponse(job);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobResponse> getRecruiterJobs() {

        User user = getCurrentUser();
        validateRecruiterRole(user);

        Recruiter recruiter = recruiterRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Recruiter profile not found"));

        return jobRepository.findByRecruiterIdOrderByCreatedAtDesc(
                        recruiter.getId()
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public JobResponse closeJob(Long jobId) {

        User user = getCurrentUser();
        validateRecruiterRole(user);

        Job job = resolveOwnedJob(jobId, user);

        job.setJobStatus(JobStatus.CLOSED);

        Job updatedJob = jobRepository.save(job);

        return mapToResponse(updatedJob);
    }

    private Job resolveOwnedJob(Long jobId, User user) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));

        Recruiter recruiter = recruiterRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Recruiter profile not found"));

        if (!job.getRecruiter().getId().equals(recruiter.getId())) {
            throw new RuntimeException("You do not have permission to modify this job.");
        }

        return job;
    }

    private Set<Branch> resolveBranches(Set<Long> branchIds) {
        if (branchIds == null || branchIds.isEmpty()) {
            return new HashSet<>();
        }

        return branchIds.stream()
                .map(id -> branchRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id)))
                .collect(Collectors.toSet());
    }

    private Set<Skill> resolveSkills(Set<Long> skillIds) {
        if (skillIds == null || skillIds.isEmpty()) {
            return new HashSet<>();
        }

        return skillIds.stream()
                .map(id -> skillRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Skill not found with id: " + id)))
                .collect(Collectors.toSet());
    }

    private void validateRecruiterRole(User user) {
        if (user.getRole() != Role.RECRUITER) {
            throw new RuntimeException("Only recruiters can perform this action");
        }
    }

    private User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found in security context.");
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Authenticated user not found: " + email));
    }

    private JobResponse mapToResponse(Job job) {

        Set<String> branchNames = job.getEligibleBranches() != null
                ? job.getEligibleBranches().stream()
                .map(Branch::getBranchName)
                .collect(Collectors.toSet())
                : new HashSet<>();

        Set<String> skillNames = job.getRequiredSkills() != null
                ? job.getRequiredSkills().stream()
                .map(Skill::getSkillName)
                .collect(Collectors.toSet())
                : new HashSet<>();

        return JobResponse.builder()
                .id(job.getId())
                .jobTitle(job.getJobTitle())
                .jobDescription(job.getJobDescription())
                .location(job.getLocation())
                .jobType(job.getJobType() != null ? job.getJobType().getDisplayName() : null)
                .salaryPackage(job.getSalaryPackage())
                .minimumCgpa(job.getMinimumCgpa())
                .applicationDeadline(job.getApplicationDeadline())
                .numberOfRounds(job.getNumberOfRounds())
                .jobStatus(job.getJobStatus() != null ? job.getJobStatus().getDisplayName() : null)
                .eligibleBranches(branchNames)
                .requiredSkills(skillNames)
                .eligibleSemesters(job.getEligibleSemesters())
                .createdAt(job.getCreatedAt())
                .build();
    }
}