package com.apexfitness.controller;

import com.apexfitness.dto.ProfessorMapper;
import com.apexfitness.dto.ProfessorRequestDTO;
import com.apexfitness.dto.ProfessorResponseDTO;
import com.apexfitness.service.ProfessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professors")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> getAll() {
        return ResponseEntity.ok(professorService.findAll().stream()
                .map(ProfessorMapper::toDTO)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ProfessorMapper.toDTO(professorService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> create(@Valid @RequestBody ProfessorRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProfessorMapper.toDTO(professorService.save(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> update(@PathVariable Long id,
                                                       @Valid @RequestBody ProfessorRequestDTO dto) {
        return ResponseEntity.ok(ProfessorMapper.toDTO(professorService.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        professorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}