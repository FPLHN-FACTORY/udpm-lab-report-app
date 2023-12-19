package com.labreportapp.labreport.core.student.repository;

import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.repository.StudentClassesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author thangncph26123
 */
@Repository
public interface StStudentClassesRepository extends StudentClassesRepository {

    Optional<StudentClasses> findStudentClassesByClassIdAndStudentId(String classId, String studentId);

    @Query(value = """
            SELECT COUNT(a.id) FROM student_classes a WHERE a.team_id = :teamId AND a.class_id = :idClass
            """, nativeQuery = true)
    Integer countStudentClasses(@Param("teamId") String teamId, @Param("idClass") String idClass);
}
