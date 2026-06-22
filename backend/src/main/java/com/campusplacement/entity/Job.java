package com.campusplacement.entity;

import com.campusplacement.enums.JobStatus;
import com.campusplacement.enums.JobType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "jobs")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id", nullable = false)
    private Recruiter recruiter;

    @Column(nullable = false, length = 200)
    private String jobTitle;

    @Column(nullable = false, length = 5000)
    private String jobDescription;

    @Column(length = 255)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType jobType;

    @Column(name = "salary_package")
    private Double salaryPackage;

    @Column(name = "minimum_cgpa")
    private Double minimumCgpa;

    @Column(name = "application_deadline")
    private LocalDate applicationDeadline;

    @Column(name = "number_of_rounds")
    private Integer numberOfRounds;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private JobStatus jobStatus = JobStatus.OPEN;

    @ManyToMany
    @JoinTable(
            name = "job_branches",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id")
    )
    @Builder.Default
    private Set<Branch> eligibleBranches = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "job_skills",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @Builder.Default
    private Set<Skill> requiredSkills = new HashSet<>();

    @ElementCollection
    @CollectionTable(
            name = "job_eligible_semesters",
            joinColumns = @JoinColumn(name = "job_id")
    )
    @Column(name = "semester")
    @Builder.Default
    private Set<Integer> eligibleSemesters = new HashSet<>();

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}