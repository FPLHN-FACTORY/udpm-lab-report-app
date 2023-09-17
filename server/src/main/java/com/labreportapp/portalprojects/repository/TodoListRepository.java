package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(TodoListRepository.NAME)
public interface TodoListRepository extends JpaRepository<TodoList, String> {

    public static final String NAME = "BaseTodoListRepository";

    @Query(value = """
            SELECT COUNT(1) FROM todo_list WHERE project_id = :projectId
            """, nativeQuery = true)
    Integer countSimpleEntityByIdProject(@Param("projectId") String projectId);

    @Query(value = """
            SELECT MAX(index_todo_list) FROM todo_list WHERE project_id = :idProject
            """, nativeQuery = true)
    Byte getIndexTodoListMax(@Param("idProject") String idProject);
}
