package com.labreportapp.portalprojects.core.member.repository;

import com.labreportapp.portalprojects.core.member.model.response.MeLabelResponse;
import com.labreportapp.portalprojects.repository.LabelRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface MeLabelRepository extends LabelRepository {

    @Query(value = """
            SELECT c.id, c.code, c.name, c.color_label FROM to_do a JOIN label_project_todo b ON a.id = b.todo_id
            JOIN label_project c ON b.label_project_id = c.id WHERE a.id = :idTodo
            """, nativeQuery = true)
    List<MeLabelResponse> getAllLabelByIdTodo(@Param("idTodo") String idTodo);

    @Query(value = """
            SELECT a.id, a.code, a.name, a.color_label 
            FROM label_project a 
            JOIN project b ON a.project_id = b.id 
            WHERE b.id = :idProject
            """, nativeQuery = true)
    List<MeLabelResponse> getAll(@Param("idProject") String idProject);

}
