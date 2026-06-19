package com.campusplacement.entity;

import com.campusplacement.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "recruiters",
        indexes = {
                @Index(
                        name = "idx_recruiter_verification_status",
                        columnList = "verification_status"
                ),
                @Index(
                        name = "idx_recruiter_company_name",
                        columnList = "company_name"
                )
        }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "company_name", nullable = false, length = 200)
    private String companyName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_id")
    private Industry industry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_size_id")
    private CompanySize companySize;

    @Column(name = "company_website", length = 255)
    private String companyWebsite;

    @Lob
    @Column(name = "company_description")
    private String companyDescription;

    @Column(name = "headquarters", length = 255)
    private String headquarters;

    @Column(name = "company_logo_url", length = 500)
    private String companyLogoUrl;

    @Column(name = "contact_person_name", length = 150)
    private String contactPersonName;

    @Column(name = "contact_person_designation", length = 150)
    private String contactPersonDesignation;

    @Column(name = "contact_phone_number", length = 20)
    private String contactPhoneNumber;

    @Column(name = "official_company_email", length = 255)
    private String officialCompanyEmail;

    @Column(name = "gst_number", length = 50)
    private String gstNumber;

    @Column(name = "registration_certificate_url", length = 500)
    private String registrationCertificateUrl;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "verification_status", nullable = false, length = 30)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    @Builder.Default
    @Column(name = "is_profile_completed", nullable = false)
    private Boolean isProfileCompleted = false;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}