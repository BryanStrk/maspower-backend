package com.apexfitness.repository;

import com.apexfitness.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    boolean existsByDni(String dni);
}