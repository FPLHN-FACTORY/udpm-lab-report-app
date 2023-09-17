package com.labreportapp.portalprojects.core.member.repository;

import com.labreportapp.portalprojects.core.member.model.response.MeImageResponse;
import com.labreportapp.portalprojects.repository.ImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface MeImageRepository extends ImageRepository {

    @Query(value = """
            SELECT COUNT(1) FROM image WHERE todo_id = :idTodo
            """, nativeQuery = true)
    Integer countImageByIdTodo(@Param("idTodo") String idTodo);

    @Query(value = """
            SELECT a.id, a.name_file, a.name_image, a.status_image, a.todo_id, a.created_date
            FROM image a WHERE a.todo_id = :idTodo 
            ORDER BY a.created_date DESC 
            """, nativeQuery = true)
    List<MeImageResponse> getAllByIdTodo(@Param("idTodo") String idTodo);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE image SET status_image = 1 WHERE status_image = 0 AND todo_id = :idTodo
            """, nativeQuery = true)
    void updateCoverOld(@Param("idTodo") String idTodo);
}
