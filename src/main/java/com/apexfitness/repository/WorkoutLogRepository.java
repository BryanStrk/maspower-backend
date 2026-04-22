package com.apexfitness.repository;

import com.apexfitness.model.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {

    /**
     * Returns all logs for a given user, ordered by completion date descending.
     */
    List<WorkoutLog> findByUserIdOrderByCompletedAtDesc(Long userId);

    /**
     * Returns logs for a user within a date range.
     * Used for weekly / monthly stats aggregation.
     */
    @Query("SELECT wl FROM WorkoutLog wl " +
            "WHERE wl.user.id = :userId " +
            "AND wl.completedAt BETWEEN :start AND :end " +
            "ORDER BY wl.completedAt DESC")
    List<WorkoutLog> findByUserIdAndCompletedAtBetween(
            @Param("userId") Long userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    /**
     * Counts total logs for a user.
     * Used for the 'totalWorkouts' stat in Profile.
     */
    long countByUserId(Long userId);

    /**
     * Sums calories burned across all logs for a user.
     * Returns 0 if the user has no logs (handled with COALESCE).
     */
    @Query("SELECT COALESCE(SUM(wl.caloriesBurned), 0) FROM WorkoutLog wl " +
            "WHERE wl.user.id = :userId")
    double sumCaloriesBurnedByUserId(@Param("userId") Long userId);
}