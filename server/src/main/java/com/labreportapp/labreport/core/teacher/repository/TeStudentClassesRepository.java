package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.labreport.core.teacher.model.response.TePointImportResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentClassesResponse;
import com.labreportapp.labreport.entity.StudentClasses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hieundph25894
 */
@Repository
public interface TeStudentClassesRepository extends JpaRepository<StudentClasses, String> {

    @Query(value = """
            SELECT 
                sc.id as id, 
                sc.student_id as idStudent,
                sc.email as emailStudent,
                sc.class_id
            FROM student_classes sc
            WHERE sc.class_id = :#{#idClass}
            ORDER BY  sc.email ASC
                     """, nativeQuery = true)
    List<TePointImportResponse> findAllStudentClassForPointByIdClass(@Param("idClass") String idClass);

    @Query(value = """
            SELECT 
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
            ORDER BY
                CASE
                  WHEN SUBSTRING(t.name, 6) REGEXP '^[0-9]+$' THEN CAST(SUBSTRING(t.name, 6) AS SIGNED)
                  ELSE NULL
                END ASC,
                CASE
                  WHEN SUBSTRING(t.name, 6) REGEXP '^[0-9]+$' THEN ROW_NUMBER() OVER (ORDER BY t.name)
                  ELSE 0
                END ASC, t.created_date ASC;
                     """, nativeQuery = true)
    List<TeStudentClassesResponse> findStudentClassByIdClass(@Param("idClass") String idClass);

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
            JOIN team t on t.id = sc.team_id
            WHERE sc.class_id = :#{#req.idClass} and sc.team_id =:#{#req.idTeam}
            ORDER BY sc.role ASC
                     """, nativeQuery = true)
    List<TeStudentClassesResponse> findStudentClassByIdClassAndIdTeam(@Param("req") TeFindStudentClasses req);

    @Query(value = """
                SELECT * FROM student_classes sc WHERE sc.id = :#{#idStudentClass}
            """, nativeQuery = true)
    StudentClasses findStudentClassesById(@Param("idStudentClass") String idStudentClass);

    @Query(value = """
                SELECT * FROM student_classes sc WHERE sc.team_id = :#{#idTeam}
            """, nativeQuery = true)
    List<StudentClasses> findAllStudentClassesByIdTeam(@Param("idTeam") String idTeam);

    @Query(value = """
            SELECT * FROM student_classes sc WHERE sc.class_id = :#{#idClass}
             """, nativeQuery = true)
    List<StudentClasses> findStudentClassesByIdClass(@Param("idClass") String idClass);

}
