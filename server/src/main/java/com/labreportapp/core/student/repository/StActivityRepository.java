package com.labreportapp.core.student.repository;

import com.labreportapp.core.common.base.SimpleEntityProjection;
import com.labreportapp.repository.ActivityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thangncph26123
 */
@Repository
public interface StActivityRepository extends ActivityRepository {

    @Query(value = """
            SELECT id, name FROM activity WHERE semester_id = :semesterId
            """, nativeQuery = true)
    List<SimpleEntityProjection> getAllActivity(@Param("semesterId") String semesterId);
}
