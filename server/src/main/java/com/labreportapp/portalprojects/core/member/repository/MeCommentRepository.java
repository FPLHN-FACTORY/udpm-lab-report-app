package com.labreportapp.portalprojects.core.member.repository;

import com.labreportapp.portalprojects.core.member.model.request.MeFindCommentRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeCommentResponse;
import com.labreportapp.portalprojects.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author thangncph26123
 */
public interface MeCommentRepository extends CommentRepository {

    @Query(value = """
            SELECT a.id, a.content, a.member_id, a.status_edit, a.todo_id, a.created_date FROM comment a
            WHERE a.todo_id = :#{#req.idTodo} 
            ORDER BY a.created_date DESC 
            """, countQuery = """
            SELECT COUNT(1) FROM comment a
            WHERE a.todo_id = :#{#req.idTodo} 
            ORDER BY a.created_date DESC 
            """, nativeQuery = true)
    Page<MeCommentResponse> getAllCommentByIdTodo(Pageable page, @Param("req") MeFindCommentRequest req);
}
