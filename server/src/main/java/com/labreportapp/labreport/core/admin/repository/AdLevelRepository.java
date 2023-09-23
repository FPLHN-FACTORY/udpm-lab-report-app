package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.request.AdFindLevelRequest;
import com.labreportapp.labreport.core.admin.model.response.AdLevelResponse;
import com.labreportapp.labreport.entity.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author quynhncph26201
 */

@Repository
public interface AdLevelRepository extends JpaRepository<Level , String> {
    @Query(" SELECT obj FROM Level obj")
    List<Level> getAllLevel(Pageable pageable);

    @Query(value = """
            SELECT * FROM level a ORDER BY a.created_date DESC
            """, nativeQuery = true)
    List<Level> findAllLevel();


    @Query(value = """
            SELECT 
            ROW_NUMBER() OVER(ORDER BY obj.created_date DESC ) AS stt ,
            obj.id,
            obj.name
            FROM level obj 
            WHERE  ( :#{#req.name} IS NULL 
                   OR :#{#req.name} LIKE '' 
                   OR obj.name LIKE %:#{#req.name}% )
                    ORDER BY obj.created_date DESC         
                    """, countQuery = """    
            SELECT COUNT(obj.id) 
            FROM level obj 
            WHERE ( :#{#req.name} IS NULL 
                    OR :#{#req.name} LIKE '' 
                    OR obj.name LIKE %:#{#req.name}% )     
                    ORDER BY obj.created_date DESC       
            """, nativeQuery = true)
    Page<AdLevelResponse> searchLevel(@Param("req") AdFindLevelRequest req, Pageable page);

    @Query(value = "SELECT COUNT(*) FROM activity  WHERE level_id = :id", nativeQuery = true)
    Integer countActivitiesByLevelId(@Param("id") String id);
}
