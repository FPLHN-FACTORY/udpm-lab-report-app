package com.labreportapp.repository;

import com.labreportapp.core.common.base.SimpleEntityProjection;
import com.labreportapp.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thangncph26123
 */
@Repository(SemesterRepository.NAME)
public interface SemesterRepository extends JpaRepository<Semester, String> {

    String NAME = "BaseSemesterRepository";

    @Query(value = """
            SELECT id, name FROM semester ORDER BY start_time DESC
            """, nativeQuery = true)
    List<SimpleEntityProjection> getAllSimpleEntityProjection();
}
