package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.Project;
import com.labreportapp.portalprojects.infrastructure.projection.SimpleEntityProj;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thangncph26123
 */

@Repository(ProjectRepository.NAME)
public interface ProjectRepository extends JpaRepository<Project, String> {

    public static final String NAME = "BaseProjectRepository";

    @Query(value = """
            SELECT id, name FROM project
            """, nativeQuery = true)
    List<SimpleEntityProj> findAllSimpleEntity();

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE project
            SET status_project =
            CASE
            WHEN end_time < UNIX_TIMESTAMP(NOW(3)) * 1000 THEN 0
            WHEN start_time <= UNIX_TIMESTAMP(NOW(3)) * 1000 AND end_time >= UNIX_TIMESTAMP(NOW(3)) * 1000 THEN 1
            WHEN start_time > UNIX_TIMESTAMP(NOW(3)) * 1000 THEN 2
            END;
            """, nativeQuery = true)
    void updateStatusProject();
}
