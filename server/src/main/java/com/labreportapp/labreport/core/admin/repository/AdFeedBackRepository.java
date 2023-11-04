package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.response.AdFeedBackResponse;
import com.labreportapp.labreport.core.admin.model.response.AdStudentClassesResponse;
import com.labreportapp.labreport.entity.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author quynhncph26201
 */
@Repository
public interface AdFeedBackRepository extends JpaRepository<FeedBack , String> {

    @Query(value = """
                 SELECT ROW_NUMBER() OVER(ORDER BY c.last_modified_date DESC ) AS stt,
                  c.descriptions as descriptions,
                  c.student_id as  idStudent,
                  c.class_id as class_id,
                  c.created_date as created_date
                  FROM feed_back c 
                  WHERE c.class_id = :#{#idClass}
                  ORDER BY created_date DESC
            """, nativeQuery = true)
    List<AdFeedBackResponse> getAllFeedBack(@Param("idClass") String idClass);

    @Query(value = """
            SELECT DISTINCT 
            sc.id as idStudentClass,
            sc.student_id as idStudent,
            sc.email as emailStudent,
            sc.role as role,
            sc.status as statusStudent,
            t.id as idTeam,
            t.code as codeTeam,
            t.name as nameTeam,
            t.subject_name as subject_name
            FROM student_classes sc
            LEFT JOIN team t on t.id = sc.team_id
            WHERE sc.class_id = :#{#idClass}
            ORDER BY t.code
                     """, countQuery = """
            SELECT COUNT(DISTINCT sc.id)
            FROM student_classes sc
            JOIN team t on t.id = sc.team_id
            WHERE sc.class_id = :#{#idClass}
            ORDER BY t.code
            """, nativeQuery = true)
    List<AdStudentClassesResponse> findStudentClassByIdClass(@Param("idClass") String idClass);

    @Query(value = """
            SELECT * FROM feed_back WHERE class_id = :idClass
            """, nativeQuery = true)
    List<FeedBack> getAllFeedBackByIdClass(@Param("idClass") String idClass);
}
