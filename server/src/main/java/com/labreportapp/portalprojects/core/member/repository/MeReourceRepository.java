package com.labreportapp.portalprojects.core.member.repository;


import com.labreportapp.portalprojects.core.member.model.response.MeResourceResponse;
import com.labreportapp.portalprojects.repository.ResourceRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface MeReourceRepository extends ResourceRepository {

    @Query(value = """
            SELECT a.id, a.name, a.url, a.todo_id, a.created_date 
            FROM resource a WHERE a.todo_id = :idTodo
            ORDER BY a.created_date DESC 
            """, nativeQuery = true)
    List<MeResourceResponse> getAll(@Param("idTodo") String idTodo);
}
