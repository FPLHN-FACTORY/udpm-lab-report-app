package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.response.AdFeedBackResponse;
import com.labreportapp.labreport.entity.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author quynhncph26201
 */
@Repository
public interface AdFeedBackRepository extends JpaRepository<FeedBack , String> {
    @Query(value = """
                 SELECT ROW_NUMBER() OVER(ORDER BY c.last_modified_date DESC ) AS stt,
                  c.descriptions as descriptions,
                  c.student_id as  idStudent,
                  c.class_id as class_id
                  FROM feed_back c 
                  WHERE c.class_id = :#{#idClass}
                  ORDER BY created_date DESC
            """, nativeQuery = true)
    List<AdFeedBackResponse> getAllFeedBack(@Param("idClass") String idClass);
}
