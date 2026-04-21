package com.apexfitness.service;

import com.apexfitness.dto.ActivityMapper;
import com.apexfitness.dto.ActivityRequestDTO;
import com.apexfitness.exception.BusinessException;
import com.apexfitness.exception.ResourceNotFoundException;
import com.apexfitness.model.Activity;
import com.apexfitness.model.Professor;
import com.apexfitness.model.User;
import com.apexfitness.repository.ActivityRepository;
import com.apexfitness.repository.ProfessorRepository;
import com.apexfitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final ProfessorRepository professorRepository;
    private final CloudinaryService cloudinaryService;

    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    public List<Activity> findFutureActivities() {
        return activityRepository.findByDateAfter(LocalDateTime.now());
    }

    public Activity findById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + id));
    }

    public Activity save(ActivityRequestDTO dto) {
        Professor professor = findProfessorById(dto.professorId());
        Activity activity = ActivityMapper.toEntity(dto, professor);
        return activityRepository.save(activity);
    }

    public Activity update(Long id, ActivityRequestDTO dto) {
        Activity existing = findById(id);
        Professor professor = findProfessorById(dto.professorId());

        // Gestión de imagen: si cambió, borrar la anterior de Cloudinary
        String oldImageUrl = existing.getImageUrl();
        String newImageUrl = dto.imageUrl();

        if (oldImageUrl != null && !oldImageUrl.isBlank() && !oldImageUrl.equals(newImageUrl)) {
            deleteImageSafely(oldImageUrl);
        }

        ActivityMapper.updateEntity(existing, dto, professor);
        return activityRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Activity activity = findById(id);

        if (activity.getImageUrl() != null && !activity.getImageUrl().isBlank()) {
            deleteImageSafely(activity.getImageUrl());
        }

        activityRepository.delete(activity);
    }

    public Activity enrollUser(Long activityId, Long userId) {
        Activity activity = findById(activityId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (!user.isActive()) {
            throw new BusinessException("User is not active");
        }

        if (activity.getUsers().contains(user)) {
            throw new BusinessException("User already enrolled in this activity");
        }

        long futureCount = activityRepository.findByDateAfter(LocalDateTime.now())
                .stream()
                .filter(a -> a.getUsers().contains(user))
                .count();

        if (futureCount >= 3) {
            throw new BusinessException("User already has 3 future activities");
        }

        activity.getUsers().add(user);
        return activityRepository.save(activity);
    }

    public Activity unenrollUser(Long activityId, Long userId) {
        Activity activity = findById(activityId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        activity.getUsers().remove(user);
        return activityRepository.save(activity);
    }

    private Professor findProfessorById(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor not found with id: " + id));
    }

    private void deleteImageSafely(String imageUrl) {
        try {
            cloudinaryService.deleteImage(imageUrl);
            log.info("Imagen eliminada de Cloudinary: {}", imageUrl);
        } catch (IOException e) {
            log.error("Error eliminando imagen de Cloudinary: {}", imageUrl, e);
        }
    }
}