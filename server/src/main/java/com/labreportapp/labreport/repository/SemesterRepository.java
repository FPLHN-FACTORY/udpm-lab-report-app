package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import com.labreportapp.labreport.entity.Semester;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = """
            SELECT id FROM semester WHERE :currentTime BETWEEN start_time AND end_time
            """, nativeQuery = true)
    String getSemesterCurrent(@Param("currentTime") Long currentTime);
}
