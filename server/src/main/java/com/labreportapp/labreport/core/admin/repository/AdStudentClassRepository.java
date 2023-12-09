package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.response.AdStudentClassesRespone;
import com.labreportapp.labreport.repository.StudentClassesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdStudentClassRepository extends StudentClassesRepository {

    @Query(value = """
            SELECT DISTINCT
                sc.id as idStudentClass,
                sc.student_id as idStudent,
                sc.email as emailStudent,
                sc.role as role,
                sc.status as statusStudent,
                t.id as idTeam,
                t.name as nameTeam,
                f.id as idFeedBack,
                att.id as idAttendance
            FROM student_classes sc
            JOIN class c ON c.id = sc.class_id
            LEFT JOIN team t on t.id = sc.team_id
            LEFT JOIN feed_back f on sc.student_id = f.student_id AND c.id = f.class_id
            LEFT JOIN attendance att on sc.student_id = att.student_id AND c.id = att.class_id
            WHERE c.id = :idClass
            GROUP BY
                sc.id, sc.student_id, sc.email, sc.role, sc.status, t.name, f.id, att.id
            ORDER BY t.name ASC
             """, nativeQuery = true)
    List<AdStudentClassesRespone> findStudentClassByIdClass(@Param("idClass") String idClass);

    @Query(value = """
            SELECT DISTINCT
            sc.id as idStudentClass,
            sc.student_id as idStudent,
            FROM student_classes sc
            WHERE sc.class_id = :#{#idStudent}
             """, nativeQuery = true)
    Optional<AdStudentClassesRespone> findStudentClassByIdStudent(@Param("idStudent") String id);

    @Query(value = """
            SELECT DISTINCT COUNT(a.id) FROM student_classes a WHERE a.class_id = :idClass
             """, nativeQuery = true)
    Integer countStudentClassesByIdClass(@Param("idClass") String idClass);

}
