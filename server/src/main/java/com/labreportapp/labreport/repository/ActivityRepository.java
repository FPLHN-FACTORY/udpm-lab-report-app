package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(ActivityRepository.NAME)
public interface ActivityRepository extends JpaRepository<Activity, String> {

    String NAME = "BaseActivityRepository";

    @Query(value = """
            SELECT a.code FROM activity a WHERE a.id = :idActivity
            """, nativeQuery = true)
    String getCodeActivity(@Param("idActivity") String idActivity);
}
