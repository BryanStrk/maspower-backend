package com.apexfitness.service;

import com.apexfitness.dto.WorkoutLogMapper;
import com.apexfitness.dto.WorkoutLogRequestDTO;
import com.apexfitness.dto.WorkoutLogResponseDTO;
import com.apexfitness.exception.BusinessException;
import com.apexfitness.exception.ResourceNotFoundException;
import com.apexfitness.model.Activity;
import com.apexfitness.model.User;
import com.apexfitness.model.WorkoutLog;
import com.apexfitness.repository.ActivityRepository;
import com.apexfitness.repository.UserRepository;
import com.apexfitness.repository.WorkoutLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Business logic for WorkoutLog (registering completed workouts).
 */
@Service
@RequiredArgsConstructor
public class WorkoutLogService {

    private final WorkoutLogRepository workoutLogRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    /**
     * Register a completed workout.
     *
     * Business rules:
     * - User must exist (404 if not)
     * - User must be active (400 if not)
     * - Activity must exist (404 if not)
     * - Cannot log a workout that hasn't happened yet (400)
     */
    @Transactional
    public WorkoutLogResponseDTO createLog(WorkoutLogRequestDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + dto.userId()));

        if (!user.isActive()) {
            throw new BusinessException("User is not active");
        }

        Activity activity = activityRepository.findById(dto.activityId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Activity not found with id: " + dto.activityId()));

        // No se puede registrar la finalización de un workout que aún no ha ocurrido
        LocalDateTime completionTime = dto.completedAt() != null ? dto.completedAt() : LocalDateTime.now();
        if (activity.getDate().isAfter(completionTime)) {
            throw new BusinessException("Cannot log a workout that has not happened yet");
        }

        WorkoutLog log = WorkoutLogMapper.toEntity(dto, user, activity);
        WorkoutLog saved = workoutLogRepository.save(log);
        return WorkoutLogMapper.toDTO(saved);
    }

    /**
     * Returns a single log by id.
     */
    @Transactional(readOnly = true)
    public WorkoutLogResponseDTO getLogById(Long id) {
        WorkoutLog log = workoutLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "WorkoutLog not found with id: " + id));
        return WorkoutLogMapper.toDTO(log);
    }

    /**
     * Returns the full history of logs for a user, most recent first.
     */
    @Transactional(readOnly = true)
    public List<WorkoutLogResponseDTO> getLogsByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        return workoutLogRepository.findByUserIdOrderByCompletedAtDesc(userId)
                .stream()
                .map(WorkoutLogMapper::toDTO)
                .toList();
    }

    /**
     * Deletes a log by id (e.g. user logged a workout by mistake).
     */
    @Transactional
    public void deleteLog(Long id) {
        if (!workoutLogRepository.existsById(id)) {
            throw new ResourceNotFoundException("WorkoutLog not found with id: " + id);
        }
        workoutLogRepository.deleteById(id);
    }
}