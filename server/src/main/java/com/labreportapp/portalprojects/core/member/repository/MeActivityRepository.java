package com.labreportapp.portalprojects.core.member.repository;

import com.labreportapp.portalprojects.core.member.model.request.MeFindActivityRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeActivityResponse;
import com.labreportapp.portalprojects.entity.Activity;
import com.labreportapp.portalprojects.repository.ActivityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author thangncph26123
 */
public interface MeActivityRepository extends ActivityRepository {

    @Query(value = """
            SELECT a.id, a.member_created_id, a.member_id, a.project_id, a.todo_id,
             a.todo_list_id, a.content_action, a.url_image, a.created_date FROM activity a 
            WHERE a.todo_id = :#{#req.idTodo}
            ORDER BY a.created_date DESC 
            """, countQuery = """
            SELECT COUNT(1) FROM activity a 
            WHERE a.todo_id = :#{#req.idTodo}
            ORDER BY a.created_date DESC 
            """, nativeQuery = true)
    Page<MeActivityResponse> getAll(Pageable pageable, @Param("req") MeFindActivityRequest req);

    @Query(value = """
            SELECT * FROM activity a 
            WHERE a.image_id = :idImage
            """, nativeQuery = true)
    Activity findActivityByIdImage(@Param("idImage") String idImage);

    @Query(value = """
            SELECT * FROM activity a 
            WHERE todo_id = :idTodo AND content LIKE :action
            """, nativeQuery = true)
    Activity findActivityByAction(@Param("idTodo") String idTodo, @Param("action") String action);
}
