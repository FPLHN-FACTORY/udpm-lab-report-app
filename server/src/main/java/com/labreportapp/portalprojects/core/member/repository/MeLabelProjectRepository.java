package com.labreportapp.portalprojects.core.member.repository;

import com.labreportapp.portalprojects.repository.LabelProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface MeLabelProjectRepository extends LabelProjectRepository {

    @Query(value = """
            SELECT a.id FROM to_do a JOIN period_todo b ON a.id = b.todo_id
            JOIN period c ON b.period_id = c.id JOIN project d ON c.project_id = d.id
            WHERE d.id = :idProject
            """, nativeQuery = true)
    List<String> getAllTodoByIdProject(@Param("idProject") String idProject);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM label_project_todo 
            WHERE label_project_id = :idLabelProject 
            AND todo_id = :idTodo
            """, nativeQuery = true)
    void deleteLabelProjectTodo(@Param("idLabelProject") String idLabelProject, @Param("idTodo") String idTodo);
}
