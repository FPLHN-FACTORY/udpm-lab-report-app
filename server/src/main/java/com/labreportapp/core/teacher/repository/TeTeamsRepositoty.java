package com.labreportapp.core.teacher.repository;

import com.labreportapp.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.core.teacher.model.response.TeStudentClassesRespone;
import com.labreportapp.core.teacher.model.response.TeTeamsRespone;
import com.labreportapp.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hieundph25894
 */
@Repository
public interface TeTeamsRepositoty extends JpaRepository<Team, String> {
    @Query(value = """
            SELECT DISTINCT 
            t.id as id,
            t.code as code,
            t.name as name,
            t.subject_name as subjectName,
            t.created_date as createdDate
            FROM team t
            WHERE t.class_id = :#{#req.idClass}
            ORDER BY t.code ASC
                     """, countQuery = """
            SELECT COUNT(DISTINCT t.id)
            FROM team t
            WHERE t.class_id = :#{#req.idClass}
            """, nativeQuery = true)
    List<TeTeamsRespone> findTeamsByIdClass(@Param("req") TeFindStudentClasses req);

    @Query(value = """
                SELECT t.code FROM team t WHERE t.code = :code
            """, nativeQuery = true)
    String getTeamByCode(@Param("code") String code);
}
