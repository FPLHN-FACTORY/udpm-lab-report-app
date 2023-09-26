package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.core.teacher.model.response.TeLevelResponse;
import com.labreportapp.labreport.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hieundph25894
 */
@Repository
public interface TeLevelRepository extends JpaRepository<Level, String> {

    @Query(value = """
                SELECT id, name FROM level ORDER BY created_date DESC
            """, nativeQuery = true)
    List<TeLevelResponse> getAllLevel();

}
