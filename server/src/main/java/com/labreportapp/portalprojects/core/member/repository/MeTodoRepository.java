package com.labreportapp.portalprojects.core.member.repository;

import com.labreportapp.portalprojects.core.member.model.request.MeFilterTodoRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeDetailTodoResponse;
import com.labreportapp.portalprojects.core.member.model.response.MeTodoResponse;
import com.labreportapp.portalprojects.repository.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface MeTodoRepository extends TodoRepository {

    @Query(value = """
            WITH CountToDoSon AS (
                SELECT a.id, 
                COUNT(g.id) AS number_todo, 
                COUNT(CASE WHEN g.status_todo = 1 THEN 1 END) AS number_todo_complete FROM to_do a
                LEFT JOIN to_do g ON g.todo_id = a.id
                WHERE a.todo_list_id = :#{#req.idTodoList}
                GROUP BY a.id
            )
            SELECT DISTINCT a.id, a.code, a.name, a.priority_level, a.descriptions, a.deadline, a.completion_time,
            a.index_todo, a.progress, e.id AS image_id, e.name_file,
            ctds.number_todo_complete,
            ctds.number_todo, a.todo_list_id
            FROM to_do a JOIN period_todo b ON a.id = b.todo_id
            JOIN period c ON b.period_id = c.id LEFT JOIN assign m ON m.todo_id = a.id
            LEFT JOIN label_project_todo n ON a.id = n.todo_id LEFT JOIN image e ON a.image_id = e.id
            LEFT JOIN CountToDoSon ctds ON ctds.id = a.id
            WHERE c.id = :#{#req.idPeriod}
            AND a.todo_list_id = :#{#req.idTodoList}
            AND IF(:#{#req.name} = '' AND 'empty' IN :#{#req.member} AND 'empty' IN :#{#req.label} 
            AND 'empty' IN :#{#req.dueDate}, 1,  (IF(:#{#req.name} = '', '', a.name LIKE %:#{#req.name}%) OR
            IF('empty' IN :#{#req.member}, '', (m.member_id IN :#{#req.member} OR
            (IF('none' IN :#{#req.member}, m.member_id IS NULL, ''))))
            OR IF('empty' IN :#{#req.label}, '', (n.label_project_id IN :#{#req.label} OR
            (IF('none' IN :#{#req.label}, n.label_project_id IS NULL, ''))))
            OR IF('empty' IN :#{#req.dueDate}, '',
            IF('noDueDate' IN :#{#req.dueDate}, a.deadline IS NULL, '') OR
            IF('overDueDate' IN :#{#req.dueDate}, a.status_todo = 4, '') OR
            IF('completeSoon' IN :#{#req.dueDate}, a.status_todo = 2, '') OR
            IF('completeLate' IN :#{#req.dueDate}, a.status_todo = 3, '') OR
            IF('notComplete' IN :#{#req.dueDate}, a.status_todo = 0, ''))))
            ORDER BY a.index_todo
            """, nativeQuery = true)
    List<MeTodoResponse> getToDoByPeriodAndIdTodoList(@Param("req") MeFilterTodoRequest req);

    @Query(value = """
            WITH CountToDoSon AS (
                SELECT a.id, 
                COUNT(g.id) AS number_todo, 
                COUNT(CASE WHEN g.status_todo = 1 THEN 1 END) AS number_todo_complete FROM to_do a
                LEFT JOIN to_do g ON g.todo_id = a.id
                WHERE a.id = :idTodo
                GROUP BY a.id
            )
            SELECT a.id, a.code, a.name, a.priority_level, a.descriptions, a.deadline, a.completion_time, a.index_todo, a.progress, e.id as image_id, e.name_file,
            ctds.number_todo_complete,
            ctds.number_todo
            FROM to_do a LEFT JOIN image e ON a.image_id = e.id 
            LEFT JOIN CountToDoSon ctds ON ctds.id = a.id
            WHERE a.id = :idTodo
            """, nativeQuery = true)
    MeTodoResponse findTodoById(@Param("idTodo") String idTodo);

    @Query(value = """
            SELECT DISTINCT a.id, a.code, a.name, a.priority_level, a.descriptions, a.deadline, a.completion_time,
            a.index_todo, a.progress, e.id as image_id, e.name_file,
            (SELECT COUNT(1) FROM to_do d WHERE d.status_todo = 1 AND d.todo_id = a.id) AS number_todo_complete,
            (SELECT COUNT(1) FROM to_do d WHERE d.todo_id = a.id) AS number_todo
            FROM to_do a JOIN period_todo b ON a.id = b.todo_id
            JOIN period c ON b.period_id = c.id LEFT JOIN assign m ON m.todo_id = a.id
            LEFT JOIN label_project_todo n ON a.id = n.todo_id LEFT JOIN image e ON a.image_id = e.id
            WHERE c.id = :idPeriod AND a.todo_list_id = :idTodoList
            AND (IF(:#{#req.name} = '', '', a.name LIKE %:#{#req.name}%) OR
            IF('empty' IN :#{#req.member}, '', (m.member_id IN :#{#req.member} OR
            (IF('none' IN :#{#req.member}, m.member_id IS NULL, ''))))
            OR IF('empty' IN :#{#req.label}, '', (n.label_project_id IN :#{#req.label} OR
            (IF('none' IN :#{#req.label}, n.label_project_id IS NULL, ''))))
            OR IF('empty' IN :#{#req.dueDate}, '',
            IF('noDueDate' IN :#{#req.dueDate}, a.deadline IS NULL, '') OR
            IF('overDueDate' IN :#{#req.dueDate}, a.status_todo = 4, '') OR
            IF('complete' IN :#{#req.dueDate}, a.status_todo = 2 OR a.status_todo = 3, '') OR
            IF('notComplete' IN :#{#req.dueDate}, a.status_todo = 0, '')))
            ORDER BY a.index_todo
            """, nativeQuery = true)
    List<MeTodoResponse> filter(@Param("req") MeFilterTodoRequest req, @Param("idPeriod") String idPeriod, @Param("idTodoList") String idTodoList);

    @Query(value = """
            SELECT DISTINCT a.id
            FROM to_do a JOIN period_todo b ON a.id = b.todo_id
            JOIN period c ON b.period_id = c.id LEFT JOIN assign m ON m.todo_id = a.id
            LEFT JOIN label_project_todo n ON a.id = n.todo_id LEFT JOIN image e ON a.image_id = e.id 
            WHERE c.id = :idPeriod AND a.todo_list_id = :idTodoList
            AND (IF(:#{#req.name} = '', '', a.name LIKE %:#{#req.name}%) OR
            IF('empty' IN :#{#req.member}, '', (m.member_id IN :#{#req.member} OR  
            (IF('none' IN :#{#req.member}, m.member_id IS NULL, ''))))
            OR IF('empty' IN :#{#req.label}, '', (n.label_project_id IN :#{#req.label} OR
            (IF('none' IN :#{#req.label}, n.label_project_id IS NULL, ''))))
            OR IF('empty' IN :#{#req.dueDate}, '', 
            IF('noDueDate' IN :#{#req.dueDate}, a.deadline IS NULL, '') OR 
            IF('overDueDate' IN :#{#req.dueDate}, a.status_todo = 4, '') OR 
            IF('complete' IN :#{#req.dueDate}, a.status_todo = 2 OR a.status_todo = 3, '') OR 
            IF('notComplete' IN :#{#req.dueDate}, a.status_todo = 0, '')))
            AND a.id = :idTodo
            ORDER BY a.index_todo
            """, nativeQuery = true)
    String checkTodoFilter(@Param("req") MeFilterTodoRequest req, @Param("idPeriod") String idPeriod, @Param("idTodoList") String idTodoList, @Param("idTodo") String idTodo);

    @Query(value = """
            SELECT a.id, a.code, a.name, a.status_todo FROM to_do a WHERE a.todo_id = :idTodo
            ORDER BY a.created_date 
            """, nativeQuery = true)
    List<MeDetailTodoResponse> getDetailTodo(@Param("idTodo") String idTodo);

    @Query(value = """
            SELECT COUNT(1) FROM to_do WHERE todo_id = :todoId AND status_todo = 1
            """, nativeQuery = true)
    Short countTodoComplete(@Param("todoId") String todoId);

    @Query(value = """
            SELECT COUNT(1) FROM to_do WHERE todo_id = :todoId
            """, nativeQuery = true)
    Short countTodoInCheckList(@Param("todoId") String todoId);

    @Query(value = """
            SELECT COUNT(1) FROM resource WHERE todo_id = :todoId
            """, nativeQuery = true)
    Integer countResourceByIdTodo(@Param("todoId") String todoId);

    @Query(value = """
            SELECT COUNT(1) FROM comment WHERE todo_id = :todoId
            """, nativeQuery = true)
    Integer countCommentByIdTodo(@Param("todoId") String todoId);

    @Query(value = """
            SELECT a.progress FROM to_do a 
            JOIN period_todo b ON a.id = b.todo_id
            WHERE b.period_id = :periodId AND a.type = 1
            """, nativeQuery = true)
    List<Short> getAllProgressByIdPeriod(@Param("periodId") String periodId);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE to_do a JOIN period_todo b ON a.id = b.todo_id
            JOIN period c ON b.period_id = c.id
            SET a.index_todo = a.index_todo - 1 
            WHERE a.index_todo > :indexBefore AND a.index_todo <= :indexAfter 
            AND c.id = :idPeriod AND a.todo_list_id = :idTodoList
            """, nativeQuery = true)
    void updateIndexTodoDecs(@Param("indexBefore") Short indexBefore, @Param("indexAfter") Short indexAfter, @Param("idPeriod") String idPeriod, @Param("idTodoList") String idTodoList);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE to_do a JOIN period_todo b ON a.id = b.todo_id
            JOIN period c ON b.period_id = c.id
            SET a.index_todo = a.index_todo + 1 
            WHERE a.index_todo < :indexBefore AND a.index_todo >= :indexAfter
            AND c.id = :idPeriod AND a.todo_list_id = :idTodoList
            """, nativeQuery = true)
    void updateIndexTodoAsc(@Param("indexBefore") Short indexBefore, @Param("indexAfter") Short indexAfter, @Param("idPeriod") String idPeriod, @Param("idTodoList") String idTodoList);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE to_do a JOIN period_todo b ON a.id = b.todo_id
            JOIN period c ON c.id = b.period_id
            SET a.index_todo = a.index_todo - 1 
            WHERE a.todo_list_id = :idTodoList AND c.id = :idPeriod
            AND a.index_todo > :indexBefore
            """, nativeQuery = true)
    void updateIndexTodoInTodoListOld(@Param("idTodoList") String idTodoList, @Param("idPeriod") String idPeriod, @Param("indexBefore") Short indexBefore);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE to_do a JOIN period_todo b ON a.id = b.todo_id
            JOIN period c ON c.id = b.period_id
            SET a.index_todo = a.index_todo + 1 
            WHERE a.todo_list_id = :idTodoList AND c.id = :idPeriod
            AND a.index_todo >= :indexAfter
            """, nativeQuery = true)
    void updateIndexTodoInTodoListNew(@Param("idTodoList") String idTodoList, @Param("idPeriod") String idPeriod, @Param("indexAfter") Short indexAfter);

    @Query(value = """
            SELECT COUNT(a.id) FROM to_do a 
            JOIN period_todo b ON a.id = b.todo_id
            JOIN period c ON c.id = b.period_id
            WHERE todo_list_id = :idTodoList AND c.id = :idPeriod
            """, nativeQuery = true)
    Short countTodoInTodoList(@Param("idTodoList") String idTodoList, @Param("idPeriod") String idPeriod);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM period_todo WHERE todo_id = :idTodo AND period_id = :idPeriod
            """, nativeQuery = true)
    void deletePeriodTodo(@Param("idTodo") String idTodo, @Param("idPeriod") String idPeriod);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM to_do WHERE todo_id = :idTodo
            """, nativeQuery = true)
    void deleteTodoInCheckList(@Param("idTodo") String idTodo);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM label_project_todo WHERE todo_id = :idTodo
            """, nativeQuery = true)
    void deleteLabelProjectTodo(@Param("idTodo") String idTodo);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM comment WHERE todo_id = :idTodo
            """, nativeQuery = true)
    void deleteCommentTodo(@Param("idTodo") String idTodo);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM activity_todo WHERE todo_id = :idTodo AND project_id = :idProject
            """, nativeQuery = true)
    void deleteActivityTodo(@Param("idTodo") String idTodo, @Param("idProject") String idProject);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM assign WHERE todo_id = :idTodo
            """, nativeQuery = true)
    void deleteAssignTodo(@Param("idTodo") String idTodo);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM image WHERE todo_id = :idTodo
            """, nativeQuery = true)
    void deleteImageTodo(@Param("idTodo") String idTodo);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM resource WHERE todo_id = :idTodo
            """, nativeQuery = true)
    void deleteResourceTodo(@Param("idTodo") String idTodo);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE to_do SET index_todo = index_todo - 1
            WHERE index_todo > :indexParam AND todo_list_id = :idTodoList
            """, nativeQuery = true)
    void updateIndexTodo(@Param("idTodoList") String idTodoList, @Param("indexParam") Integer indexParam);

    @Modifying
    @Transactional
    @Query(value = """
                UPDATE to_do t
                 JOIN (
                   SELECT g.id, ROW_NUMBER() OVER 
                   (PARTITION BY g.todo_list_id ORDER BY COALESCE(g.priority_level, 9999999) ASC) AS new_index
                   FROM to_do g
                   JOIN period_todo h ON g.id = h.todo_id
                   WHERE h.period_id = :idPeriod
                 ) t2 ON t.id = t2.id
                 SET t.index_todo = t2.new_index - 1 
            """, nativeQuery = true)
    void sortByPriorityAsc(@Param("idPeriod") String idPeriod);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE to_do t
            JOIN (
              SELECT g.id, ROW_NUMBER() OVER (PARTITION BY g.todo_list_id ORDER BY COALESCE(g.priority_level, 9999999) DESC) AS new_index
                   FROM to_do g
                   JOIN period_todo h ON g.id = h.todo_id
                   WHERE h.period_id = :idPeriod
            ) t2 ON t.id = t2.id 
            SET t.index_todo = t2.new_index - 1;
            """, nativeQuery = true)
    void sortByPriorityDesc(@Param("idPeriod") String idPeriod);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE to_do t
             JOIN (
               SELECT g.id, ROW_NUMBER() OVER (PARTITION BY g.todo_list_id ORDER BY COALESCE(g.deadline, 9999999) ASC) AS new_index
                   FROM to_do g
                   JOIN period_todo h ON g.id = h.todo_id
                   WHERE h.period_id = :idPeriod
             ) t2 ON t.id = t2.id 
              SET t.index_todo = t2.new_index - 1 
            """, nativeQuery = true)
    void sortByDeadlineAsc(@Param("idPeriod") String idPeriod);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE to_do t
            JOIN (
              SELECT g.id, ROW_NUMBER() OVER (PARTITION BY g.todo_list_id ORDER BY COALESCE(g.deadline, 9999999) DESC ) AS new_index
                   FROM to_do g
                   JOIN period_todo h ON g.id = h.todo_id
                   WHERE h.period_id = :idPeriod
            ) t2 ON t.id = t2.id 
            SET t.index_todo = t2.new_index - 1 
            """, nativeQuery = true)
    void sortByDeadlineDesc(@Param("idPeriod") String idPeriod);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE to_do t
             JOIN (
               SELECT g.id, ROW_NUMBER() OVER (PARTITION BY g.todo_list_id ORDER BY COALESCE(g.created_date, 9999999) ASC) AS new_index
                   FROM to_do g
                   JOIN period_todo h ON g.id = h.todo_id
                   WHERE h.period_id = :idPeriod
             ) t2 ON t.id = t2.id 
              SET t.index_todo = t2.new_index - 1 
            """, nativeQuery = true)
    void sortByCreatedDateAsc(@Param("idPeriod") String idPeriod);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE to_do t
            JOIN (
              SELECT g.id, ROW_NUMBER() OVER (PARTITION BY g.todo_list_id ORDER BY COALESCE(g.created_date, 9999999) DESC ) AS new_index
                   FROM to_do g
                   JOIN period_todo h ON g.id = h.todo_id
                   WHERE h.period_id = :idPeriod
            ) t2 ON t.id = t2.id 
            SET t.index_todo = t2.new_index - 1 
            """, nativeQuery = true)
    void sortByCreatedDateDesc(@Param("idPeriod") String idPeriod);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE to_do t
             JOIN (
               SELECT g.id, ROW_NUMBER() OVER (PARTITION BY g.todo_list_id ORDER BY COALESCE(g.name, 9999999) ASC) AS new_index
                   FROM to_do g
                   JOIN period_todo h ON g.id = h.todo_id
                   WHERE h.period_id = :idPeriod
             ) t2 ON t.id = t2.id 
              SET t.index_todo = t2.new_index - 1 
            """, nativeQuery = true)
    void sortByNameAsc(@Param("idPeriod") String idPeriod);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE to_do t
            JOIN (
              SELECT g.id, ROW_NUMBER() OVER (PARTITION BY g.todo_list_id ORDER BY COALESCE(g.name, 9999999) DESC) AS new_index
                   FROM to_do g
                   JOIN period_todo h ON g.id = h.todo_id
                   WHERE h.period_id = :idPeriod
            ) t2 ON t.id = t2.id 
            SET t.index_todo = t2.new_index - 1 
            """, nativeQuery = true)
    void sortByNameDesc(@Param("idPeriod") String idPeriod);

    @Modifying
    @Transactional
    @Query(value = """
                UPDATE to_do t
                 JOIN (
                 SELECT g.id, ROW_NUMBER() OVER 
                   (PARTITION BY g.todo_list_id ORDER BY COALESCE(g.progress, 9999999) ASC) AS new_index
                   FROM to_do g
                   JOIN period_todo h ON g.id = h.todo_id
                   WHERE h.period_id = :idPeriod
                 ) t2 ON t.id = t2.id
                 SET t.index_todo = t2.new_index - 1 
            """, nativeQuery = true)
    void sortByProgressAsc(@Param("idPeriod") String idPeriod);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE to_do t
            JOIN (
              SELECT g.id, ROW_NUMBER() OVER 
                   (PARTITION BY g.todo_list_id ORDER BY COALESCE(g.progress, 9999999) DESC) AS new_index
                   FROM to_do g
                   JOIN period_todo h ON g.id = h.todo_id
                   WHERE h.period_id = :idPeriod
            ) t2 ON t.id = t2.id 
            SET t.index_todo = t2.new_index - 1 
            """, nativeQuery = true)
    void sortByProgressDesc(@Param("idPeriod") String idPeriod);

    @Query(value = """
            SELECT a.id FROM member_project a
            WHERE a.member_id = :idMember AND a.project_id = :idProject
            """, nativeQuery = true)
    String checkMemberProject(@Param("idProject") String idProject, @Param("idMember") String idMember);

}
