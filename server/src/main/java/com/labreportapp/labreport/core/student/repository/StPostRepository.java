package com.labreportapp.labreport.core.student.repository;

import com.labreportapp.labreport.core.student.model.request.StFindPostRequest;
import com.labreportapp.labreport.core.student.model.response.StPostResponse;
import com.labreportapp.labreport.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author quynhncph26201
 */
@Repository

public interface StPostRepository extends JpaRepository<Post, String> {

    @Query(value = """
            SELECT DISTINCT
            p.id as id, 
            p.descriptions as descriptions,
             p.created_date as created_date,
             p.teacher_id,
             p.class_id
            FROM post p 
            WHERE p.class_id = :#{#req.idClass}
            ORDER BY p.created_date DESC 
            """,countQuery = """
            SELECT COUNT(DISTINCT p.id)
            FROM post p 
            WHERE p.class_id = :#{#req.idClass}
            """
            , nativeQuery = true)
    Page<StPostResponse> searchPostByIdClass(@Param("req") StFindPostRequest req, Pageable pageable);
}
