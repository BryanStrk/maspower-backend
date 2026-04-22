package com.apexfitness.model;

/**
 * User roles in the Apex Fitness platform.
 *
 * - CLIENT: regular gym member, can browse and enroll in workouts
 * - ADMIN: can manage users, activities and view global stats
 *
 * Note: trainers are modeled as a separate Professor entity, not as
 * a User role. If in the future trainers also need to be users of
 * the app, a TRAINER role may be added here.
 */
public enum Role {
    CLIENT,
    ADMIN
}