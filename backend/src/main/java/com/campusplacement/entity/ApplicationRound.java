package com.campusplacement.entity;

import com.campusplacement.enums.RoundStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "application_rounds",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "application_id",
                                "selection_round_id"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ApplicationRound {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selection_round_id", nullable = false)
    private SelectionRound selectionRound;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private RoundStatus roundStatus = RoundStatus.PENDING;


}