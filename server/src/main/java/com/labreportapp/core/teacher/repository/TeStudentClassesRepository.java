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
            sc.id as idStudentClasses,
            sc.student_id as idStudent,
            sc.email as emailStudent,
            sc.status as statusStudent,
            t.id as idTeam,
            t.code as codeTeam
            FROM student_classes sc
            LEFT JOIN team t on t.id = sc.team_id
            WHERE sc.class_id = :#{#req.idClass}
                     """, countQuery = """
            SELECT COUNT(DISTINCT sc.id)
            FROM student_classes sc
            JOIN team t on t.id = sc.team_id
            WHERE sc.class_id = :#{#req.idClass}
            """, nativeQuery = true)
    List<TeStudentClassesRespone> findStudentClassByIdTeacherAndIdClass(@Param("req") TeFindStudentClasses req);

    @Query(value = """
                SELECT * FROM student_classes sc WHERE sc.id = :#{#idStudentClass}
            """, nativeQuery = true)
    StudentClasses findStudentClassesById(@Param("idStudentClass") String idStudentClass);

}
