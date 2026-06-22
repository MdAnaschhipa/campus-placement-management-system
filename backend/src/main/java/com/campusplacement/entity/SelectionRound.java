package com.campusplacement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "selection_rounds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectionRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Column(nullable = false)
    private Integer roundNumber;

    @Column(nullable = false, length = 200)
    private String roundName;
}