package com.maspower.service;

import com.maspower.exception.BusinessException;
import com.maspower.exception.ResourceNotFoundException;
import com.maspower.model.Activity;
import com.maspower.model.User;
import com.maspower.repository.ActivityRepository;
import com.maspower.repository.UserRepository;
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

    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    public Activity update(Long id, Activity activity) {
        Activity existing = findById(id);

        // Si la imagen ha cambiado, borrar la anterior de Cloudinary
        String oldImageUrl = existing.getImageUrl();
        String newImageUrl = activity.getImageUrl();

        if (oldImageUrl != null && !oldImageUrl.isBlank() && !oldImageUrl.equals(newImageUrl)) {
            deleteImageSafely(oldImageUrl);
        }

        existing.setTitle(activity.getTitle());
        existing.setDescription(activity.getDescription());
        existing.setPrice(activity.getPrice());
        existing.setDate(activity.getDate());
        existing.setImageUrl(newImageUrl);
        existing.setProfessor(activity.getProfessor());

        return activityRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Activity activity = findById(id);

        // Borrar imagen de Cloudinary antes de eliminar la entidad
        if (activity.getImageUrl() != null && !activity.getImageUrl().isBlank()) {
            deleteImageSafely(activity.getImageUrl());
        }

        activityRepository.delete(activity);
    }

    public Activity enrollUser(Long activityId, Long userId) {
        Activity activity = findById(activityId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Criterio: usuario debe estar activo
        if (!user.isActive()) {
            throw new BusinessException("User is not active");
        }

        // Criterio: no puede inscribirse 2 veces
        if (activity.getUsers().contains(user)) {
            throw new BusinessException("User already enrolled in this activity");
        }

        // Criterio: máximo 3 actividades futuras
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

    /**
     * Borra una imagen de Cloudinary sin propagar el error.
     * Si falla, se registra en logs pero no se interrumpe la operación principal.
     */
    private void deleteImageSafely(String imageUrl) {
        try {
            cloudinaryService.deleteImage(imageUrl);
            log.info("Imagen eliminada de Cloudinary: {}", imageUrl);
        } catch (IOException e) {
            log.error("Error eliminando imagen de Cloudinary: {}", imageUrl, e);
        }
    }
}