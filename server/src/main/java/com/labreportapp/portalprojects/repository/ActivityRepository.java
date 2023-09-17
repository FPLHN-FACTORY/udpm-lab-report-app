package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(ActivityRepository.NAME)
public interface ActivityRepository extends JpaRepository<Activity, String> {

    public static final String NAME = "BaseActivityRepository";
}
