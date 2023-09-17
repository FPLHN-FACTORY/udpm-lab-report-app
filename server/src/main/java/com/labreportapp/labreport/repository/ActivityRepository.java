package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(ActivityRepository.NAME)
public interface ActivityRepository extends JpaRepository<Activity, String> {

    String NAME = "BaseActivityRepository";
}
