package com.apexfitness.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "professors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @Column(nullable = false)
    @ToString.Include
    private String name;

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(name = "hiring_year", nullable = false)
    private int hiringYear;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "image_url")
    private String imageUrl;
}