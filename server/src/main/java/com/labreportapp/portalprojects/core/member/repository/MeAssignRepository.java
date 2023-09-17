package com.labreportapp.portalprojects.core.member.repository;

import com.labreportapp.portalprojects.entity.Assign;
import com.labreportapp.portalprojects.repository.AssignRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface MeAssignRepository extends AssignRepository {

    @Query(value = """
            SELECT b.member_id FROM to_do a JOIN assign b ON a.id = b.todo_id
            WHERE a.id = :idTodo
            """, nativeQuery = true)
    List<String> getAllMemberByIdTodo(@Param("idTodo") String idTodo);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM assign a
            WHERE a.member_id = :idMember AND a.todo_id = :idTodo
            """, nativeQuery = true)
    void delete(@Param("idMember") String idMember, @Param("idTodo") String idTodo);

    @Query(value = """
            SELECT * FROM assign a
            WHERE a.member_id = :idMember AND a.todo_id = :idTodo
            """, nativeQuery = true)
    Assign findByMemberIdAndTodoId(@Param("idMember") String idMember, @Param("idTodo") String idTodo);
}
