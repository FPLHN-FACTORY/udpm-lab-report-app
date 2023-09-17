package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.Assign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thangncph26123
 */
@Repository(AssignRepository.NAME)
public interface AssignRepository extends JpaRepository<Assign, String> {

    public static final String NAME = "BaseAssignRepository";

    @Query(value = """
            SELECT * FROM assign WHERE todo_id = :todoId
            """, nativeQuery = true)
    List<Assign> getAllAssignByIdTodo(@Param("todoId") String todoId);
}
