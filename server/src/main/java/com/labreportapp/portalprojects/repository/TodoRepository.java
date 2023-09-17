package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.core.member.model.response.MeDataDashboardLabelResponse;
import com.labreportapp.portalprojects.core.member.model.response.MeDataDashboardMemberResponse;
import com.labreportapp.portalprojects.core.member.model.response.MeDataDashboardTodoListResoonse;
import com.labreportapp.portalprojects.entity.Todo;
import com.labreportapp.portalprojects.infrastructure.projection.SimpleEntityProj;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thangncph26123
 */
@Repository(TodoRepository.NAME)
public interface TodoRepository extends JpaRepository<Todo, String> {

    public static final String NAME = "BaseTodoRepository";

    @Query(value = """
            SELECT id, name FROM todo
            """, nativeQuery = true)
    List<SimpleEntityProj> findAllSimpleEntity();

    @Query(value = """
            SELECT COUNT(1) FROM to_do WHERE todo_list_id = :todoListId AND todo_id IS NULL
            """, nativeQuery = true)
    Integer countSimpleEntityByIdTodo(@Param("todoListId") String todoListId);

    @Query(value = """
            SELECT MAX(a.index_todo) FROM to_do a JOIN period_todo b ON a.id = b.todo_id
            JOIN period c ON c.id = b.period_id
            WHERE a.todo_list_id = :todoListId AND a.todo_id IS NULL
            AND c.id = :idPeriod
            """, nativeQuery = true)
    Short getIndexTodoMax(@Param("todoListId") String todoListId, @Param("idPeriod") String idPeriod);

    @Query(value = """
            SELECT a.name, (SELECT COUNT(1) FROM to_do b 
            WHERE b.todo_list_id = a.id AND b.todo_id IS NULL) as List
            FROM todo_list a WHERE a.project_id = :projectId ORDER BY a.index_todo_list ASC
            """, nativeQuery = true)
    List<MeDataDashboardTodoListResoonse> countTodoByTodoListAllProject(@Param("projectId") String projectId);

    @Query(value = """
            SELECT COUNT(1) FROM to_do a 
            JOIN todo_list b ON a.todo_list_id = b.id
            WHERE a.type = 1 AND a.todo_id IS NULL 
            AND b.project_id = :projectId 
            AND a.status_todo = :statusTodo
            """, nativeQuery = true)
    Integer countTodoByDueDateAllProject(@Param("projectId") String projectId,
                                         @Param("statusTodo") Integer statusTodo);

    @Query(value = """
            SELECT COUNT(1) FROM to_do a 
            JOIN todo_list b ON a.todo_list_id = b.id
            WHERE a.type = 1 AND a.todo_id IS NULL 
            AND b.project_id = :projectId 
            AND a.deadline IS NULL
            """, nativeQuery = true)
    Integer countTodoByNoDueDateAllProject(@Param("projectId") String projectId);

    @Query(value = """
            SELECT DISTINCT
                SUBSTRING_INDEX(c.email, '@', 1) AS name, (select COUNT(1) from to_do g JOIN assign k ON g.id = k.todo_id\s
                WHERE g.todo_id IS NULL AND k.email = c.email) as member
            FROM
                todo_list a
                JOIN to_do b ON b.todo_list_id = a.id
                LEFT JOIN assign c ON c.todo_id = b.id
            WHERE
                a.project_id = :projectId
                AND c.email IS NOT NULL;
            """, nativeQuery = true)
    List<MeDataDashboardMemberResponse> countTodoByMemberAllProject(@Param("projectId") String projectId);

    @Query(value = """
            SELECT COUNT(1)
            FROM to_do a
            JOIN todo_list b ON a.todo_list_id = b.id
            LEFT JOIN assign c ON a.id = c.todo_id
            WHERE a.type = 1
            AND a.todo_id IS NULL
            AND b.project_id = :projectId 
            AND c.id IS NULL;
            """, nativeQuery = true)
    Integer countTodoByNoMemberAllProject(@Param("projectId") String projectId);

    @Query(value = """
                select DISTINCT a.name, (Select COUNT(1) from to_do g
                JOIN label_project_todo k ON g.id = k.todo_id WHERE k.label_project_id = a.id) as label from label_project a
                JOIN label_project_todo b ON a.id = b.label_project_id
                JOIN to_do c ON c.id = b.todo_id
                WHERE a.project_id = :projectId 
            """, nativeQuery = true)
    List<MeDataDashboardLabelResponse> countTodoByLabelAllProject(@Param("projectId") String projectId);

    @Query(value = """
            SELECT COUNT(1)
            FROM to_do a
            JOIN todo_list b ON a.todo_list_id = b.id
            LEFT JOIN label_project_todo c ON a.id = c.todo_id
            WHERE a.type = 1
            AND a.todo_id IS NULL
            AND b.project_id = :projectId 
            AND c.id IS NULL;
            """, nativeQuery = true)
    Integer countTodoByNoLabelAllProject(@Param("projectId") String projectId);

    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Query(value = """
            SELECT a.name, (SELECT COUNT(1) FROM to_do b JOIN period_todo i ON b.id = i.todo_id
            WHERE b.todo_list_id = a.id AND b.todo_id IS NULL AND i.period_id = :periodId) as List
            FROM todo_list a WHERE a.project_id = :projectId 
            ORDER BY a.index_todo_list ASC
            """, nativeQuery = true)
    List<MeDataDashboardTodoListResoonse> countTodoByTodoListPeriod(@Param("projectId") String projectId,
                                      @Param("periodId") String periodId);

    @Query(value = """
            SELECT COUNT(1) FROM to_do a 
            JOIN todo_list b ON a.todo_list_id = b.id
            JOIN period_todo c ON a.id = c.todo_id
            WHERE a.type = 1 AND a.todo_id IS NULL 
            AND b.project_id = :projectId 
            AND a.status_todo = :statusTodo
            AND c.period_id = :periodId
            """, nativeQuery = true)
    Integer countTodoByDueDatePeriod(@Param("projectId") String projectId,
                                     @Param("periodId") String periodId,
                                     @Param("statusTodo") Integer statusTodo);

    @Query(value = """
            SELECT COUNT(1) FROM to_do a 
            JOIN todo_list b ON a.todo_list_id = b.id
            JOIN period_todo c ON a.id = c.todo_id
            WHERE a.type = 1 AND a.todo_id IS NULL 
            AND b.project_id = :projectId 
            AND a.deadline IS NULL
            AND c.period_id = :periodId
            """, nativeQuery = true)
    Integer countTodoByNoDueDatePeriod(@Param("projectId") String projectId,
                                       @Param("periodId") String periodId);

    @Query(value = """
            SELECT DISTINCT
                SUBSTRING_INDEX(c.email, '@', 1) AS name, (select COUNT(1) from to_do g 
                 JOIN period_todo h ON g.id = h.todo_id
                JOIN assign k ON g.id = k.todo_id
                WHERE g.todo_id IS NULL AND k.email = c.email AND h.period_id = :periodId) as member
            FROM
                todo_list a
                JOIN to_do b ON b.todo_list_id = a.id
                LEFT JOIN assign c ON c.todo_id = b.id
            WHERE
                a.project_id = :projectId
                AND c.email IS NOT NULL 
            """, nativeQuery = true)
    List<MeDataDashboardMemberResponse> countTodoByMemberPeriod(@Param("projectId") String projectId,
                                    @Param("periodId") String periodId);

    @Query(value = """
            SELECT COUNT(1)
            FROM to_do a
            JOIN todo_list b ON a.todo_list_id = b.id
            LEFT JOIN assign c ON a.id = c.todo_id
            JOIN period_todo d ON a.id = d.todo_id
            WHERE a.type = 1
            AND a.todo_id IS NULL
            AND b.project_id = :projectId 
            AND c.id IS NULL
            AND d.period_id = :periodId
            """, nativeQuery = true)
    Integer countTodoByNoMemberPeriod(@Param("projectId") String projectId,
                                      @Param("periodId") String periodId);

    @Query(value = """
            select DISTINCT a.name, (Select COUNT(1) from to_do g JOIN period_todo h ON g.id = h.todo_id
                JOIN label_project_todo k ON g.id = k.todo_id WHERE k.label_project_id = a.id
                AND h.period_id = :periodId) as label from label_project a
                JOIN label_project_todo b ON a.id = b.label_project_id
                JOIN to_do c ON c.id = b.todo_id
                WHERE a.project_id = :projectId 
            """, nativeQuery = true)
    List<MeDataDashboardLabelResponse> countTodoByLabelPeriod(@Param("projectId") String projectId,
                                   @Param("periodId") String periodId);

    @Query(value = """
            SELECT COUNT(1)
            FROM to_do a
            JOIN todo_list b ON a.todo_list_id = b.id
            LEFT JOIN label_project_todo c ON a.id = c.todo_id
            JOIN period_todo d ON a.id = d.todo_id
            WHERE a.type = 1
            AND a.todo_id IS NULL
            AND b.project_id = :projectId 
            AND c.id IS NULL
            AND d.period_id = :periodId
            """, nativeQuery = true)
    Integer countTodoByNoLabelPeriod(@Param("projectId") String projectId,
                                     @Param("periodId") String periodId);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE to_do
            SET status_todo = 4
            WHERE deadline < UNIX_TIMESTAMP(NOW(3)) * 1000 AND completion_time IS NULL;
            """, nativeQuery = true)
    void updateStatusTodoOverDueDate();

    @Query(value = """
            SELECT * FROM to_do WHERE reminder_time IS NOT NULL AND status_reminder = 0 AND type = 1
            """, nativeQuery = true)
    List<Todo> getAllTodoReminder();

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE to_do
            SET status_reminder = 1
            WHERE id = :todoId
            """, nativeQuery = true)
    void updateStatusReminder(@Param("todoId") String todoId);

    @Query(value = """
            SELECT b.period_id FROM to_do a 
            JOIN period_todo b ON a.id = b.todo_id
            WHERE a.id = :todoId
            """, nativeQuery = true)
    String getPeriodByIdTodo(@Param("todoId") String todoId);

    @Query(value = """
            SELECT c.id FROM to_do a 
            JOIN todo_list b ON a.todo_list_id = b.id
            JOIN project c ON b.project_id = c.id
            WHERE a.id = :todoId
            """, nativeQuery = true)
    String getProjectByIdTodo(@Param("todoId") String todoId);

    @Query(value = """
            SELECT SUM(a.progress)
            FROM to_do a
            JOIN todo_list b ON a.todo_list_id = b.id
            JOIN project c ON b.project_id = c.id
            WHERE c.id = :projectId AND a.progress IS NOT NULL
            """, nativeQuery = true)
    Float getAllTodoInProject(@Param("projectId") String projectId);
}
