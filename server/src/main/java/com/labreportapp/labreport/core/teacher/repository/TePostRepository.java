package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.core.teacher.model.request.TeFindPostClassRepquest;
import com.labreportapp.labreport.core.teacher.model.response.TePostResponse;
import com.labreportapp.labreport.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author hieundph25894
 */
@Repository
public interface TePostRepository extends JpaRepository<Post, String> {

    @Query(value = """
            SELECT DISTINCT
            p.id as id, 
            p.descriptions as descriptions,
             p.created_date as created_date,
             p.teacher_id as teacher_id,
             p.class_id
            FROM post p 
            WHERE p.class_id = :#{#req.idClass}
            ORDER BY p.created_date DESC 
            """, countQuery = """
            SELECT COUNT(DISTINCT p.id)
            FROM post p 
            WHERE p.class_id = :#{#req.idClass}"""
            , nativeQuery = true)
    Page<TePostResponse> searchPostByIdClass(@Param("req") TeFindPostClassRepquest req, Pageable pageable);

}
