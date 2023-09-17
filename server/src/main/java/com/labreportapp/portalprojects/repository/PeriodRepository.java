package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.Period;
import com.labreportapp.portalprojects.infrastructure.projection.SimpleEntityProj;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thangncph26123
 */
@Repository(PeriodRepository.NAME)
public interface PeriodRepository extends JpaRepository<Period, String> {

    public static final String NAME = "BasePeriodRepository";

    @Query(value = """
            SELECT id, name FROM period ORDER BY created_date DESC
            """, nativeQuery = true)
    List<SimpleEntityProj> findAllSimpleEntity();

    @Query(value = """
            SELECT COUNT(1) FROM period WHERE project_id = :projectId
            """, nativeQuery = true)
    Integer countSimpleEntityByIdProject(@Param("projectId") String projectId);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE period
            SET status_period =
            CASE
            WHEN end_time < UNIX_TIMESTAMP(NOW(3)) * 1000 THEN 0
            WHEN start_time <= UNIX_TIMESTAMP(NOW(3)) * 1000 AND end_time >= UNIX_TIMESTAMP(NOW(3)) * 1000 THEN 1
            WHEN start_time > UNIX_TIMESTAMP(NOW(3)) * 1000 THEN 2
            END;
            """, nativeQuery = true)
    void updateStatusPeriod();
}
