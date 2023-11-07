package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(ClassRepository.NAME)
public interface ClassRepository extends JpaRepository<Class, String> {

    String NAME = "BaseClassRepository";

    @Query(value = """
            SELECT a.code FROM class a JOIN activity b ON a.activity_id = b.id
            WHERE b.id = :idActivity
            ORDER BY CAST(SUBSTRING_INDEX(a.code, '_', -1) AS SIGNED) DESC
            LIMIT 1
            """, nativeQuery = true)
    String getMaNhomMaxByIdActivity(@Param("idActivity") String idActivity);
}
