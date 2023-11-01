package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.entity.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hieundph25894 - duchieu212
 */
@Repository
public interface TeFeedbackRepository extends JpaRepository<FeedBack, String> {

    @Query(value = """
            SELECT * FROM feed_back WHERE class_id = :idClass
            """, nativeQuery = true)
    List<FeedBack> getAllFeedBackByIdClass(@Param("idClass") String idClass);
}
