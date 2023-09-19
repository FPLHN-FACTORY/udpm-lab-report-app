package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import com.labreportapp.labreport.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thangncph26123
 */
@Repository(LevelRepository.NAME)
public interface LevelRepository extends JpaRepository<Level, String> {

    String NAME = "BaseLevelRepository";

    @Query(value = """
            SELECT id, name FROM level ORDER BY created_date DESC
            """, nativeQuery = true)
    List<SimpleEntityProjection> getAllSimpleEntityProjection();
}
