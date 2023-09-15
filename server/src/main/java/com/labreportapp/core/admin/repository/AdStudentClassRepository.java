package com.labreportapp.core.admin.repository;

import com.labreportapp.core.admin.model.response.AdStudentClassesRespone;
import com.labreportapp.repository.StudentClassesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
                        t.code as codeTeam,
                        t.name as nameTeam
                        FROM student_classes sc
                        LEFT JOIN team t on t.id = sc.team_id
                        WHERE sc.class_id = :#{#idClass}
                        ORDER BY t.code
                         """, nativeQuery = true)
    List<AdStudentClassesRespone> findStudentClassByIdClass(@Param("idClass") String idClass);
}
