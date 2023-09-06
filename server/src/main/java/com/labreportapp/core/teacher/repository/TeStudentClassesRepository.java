package com.labreportapp.core.teacher.repository;

import com.labreportapp.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.core.teacher.model.response.TeStudentClassesRespone;
import com.labreportapp.entity.StudentClasses;
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
            SELECT DISTINCT 
            sc.id as idStudentClass,
            sc.student_id as idStudent,
            sc.email as emailStudent,
            sc.role as role,
            sc.status as statusStudent,
            t.id as idTeam,
            t.code as codeTeam,
            t.name as nameTeam
            FROM student_classes sc
            LEFT JOIN team t on t.id = sc.team_id
            WHERE sc.class_id = :#{#req.idClass}
            ORDER BY t.code
                     """, countQuery = """
            SELECT COUNT(DISTINCT sc.id)
            FROM student_classes sc
            JOIN team t on t.id = sc.team_id
            WHERE sc.class_id = :#{#req.idClass}
            ORDER BY t.code
            """, nativeQuery = true)
    List<TeStudentClassesRespone> findStudentClassByIdClass(@Param("req") TeFindStudentClasses req);

    @Query(value = """
            SELECT DISTINCT 
            sc.id as idStudentClass,
            sc.student_id as idStudent,
            sc.email as emailStudent,
            sc.role as role,
            sc.status as statusStudent,
            t.id as idTeam,
            t.code as codeTeam,
            t.name as nameTeam
            FROM student_classes sc
            JOIN team t on t.id = sc.team_id
            WHERE sc.class_id = :#{#req.idClass} and sc.team_id =:#{#req.idTeam}
                     """, countQuery = """
            SELECT COUNT(DISTINCT sc.id)
            FROM student_classes sc
            JOIN team t on t.id = sc.team_id
            WHERE sc.class_id = :#{#req.idClass} and sc.team_id =:#{#req.idTeam}
              """, nativeQuery = true)
    List<TeStudentClassesRespone> findStudentClassByIdClassAndIdTeam(@Param("req") TeFindStudentClasses req);

    @Query(value = """
                SELECT * FROM student_classes sc WHERE sc.id = :#{#idStudentClass}
            """, nativeQuery = true)
    StudentClasses findStudentClassesById(@Param("idStudentClass") String idStudentClass);

    @Query(value = """
                SELECT * FROM student_classes sc WHERE sc.team_id = :#{#idTeam}
            """, nativeQuery = true)
    List<StudentClasses> findAllStudentClassesByIdTeam(@Param("idTeam") String idTeam);

}
