package com.labreportapp.core.teacher.repository;

import com.labreportapp.core.teacher.model.request.TeFindClassRequest;
import com.labreportapp.core.teacher.model.response.TeClassResponse;
import com.labreportapp.core.teacher.model.response.TeDetailClassRespone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.labreportapp.entity.Class;

import java.util.Optional;

/**
 * @author hieundph25894
 */
@Repository
public interface TeClassRepository extends JpaRepository<Class, String> {

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY c.last_modified_date DESC ) AS stt,
            c.code as code,
            c.id as id,
            c.name as name,
            c.start_time as start_time,
            c.password as password,
            c.class_period as class_period,
            c.class_size as class_size,
            c.teacher_id as teacher_id,
            c.activity_id as activity_id,
            c.created_date as created_date,
            c.descriptions as descriptions,
            a.level as level
            FROM activity a
            JOIN class c ON c.activity_id = a.id
            JOIN semester s ON s.id = a.semester_id
            where c.teacher_id = :#{#req.idTeacher}
            and (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR :#{#req.idSemester} LIKE s.id)
            and (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR :#{#req.idActivity} LIKE a.id)
            and (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR c.code LIKE %:#{#req.code}%)
            and (:#{#req.name} IS NULL OR :#{#req.name} LIKE '' OR c.name LIKE %:#{#req.name}%)
            and (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR  c.class_period = :#{#req.classPeriod})
            and (:#{#req.level} IS NULL OR :#{#req.level} LIKE '' OR a.level = :#{#req.level})
            ORDER BY c.last_modified_date DESC
                     """,countQuery = """
            SELECT COUNT(c.id) 
             FROM activity a
            JOIN class c ON c.activity_id = a.id
            JOIN semester s ON s.id = a.semester_id
             WHERE c.teacher_id = :#{#req.idTeacher}
            and (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR :#{#req.idSemester} LIKE s.id)
            and (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR :#{#req.idActivity} LIKE a.id)
            and (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR c.code LIKE %:#{#req.code}%)
            and (:#{#req.name} IS NULL OR :#{#req.name} LIKE '' OR c.name LIKE %:#{#req.name}%)
            and (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR  c.class_period = :#{#req.classPeriod})
            and (:#{#req.level} IS NULL OR :#{#req.level} LIKE '' OR a.level = :#{#req.level})
            """ ,nativeQuery = true)
    Page<TeClassResponse> findClassBySemesterAndActivity(@Param("req") TeFindClassRequest req, Pageable pageable);

    @Query(value = """
            SELECT c.id as id,
            c.code as code,
            c.name as name,
            c.start_time as start_time,
            c.password as password,
            c.class_period as class_period,
            c.class_size as class_size,
            a.name as activityName,
            c.descriptions as descriptions,
            a.level as activityLevel,
            s.name as semesterName
            FROM activity a
            JOIN class c ON c.activity_id = a.id
            JOIN semester s ON s.id = a.semester_id
            where c.id = :#{#id}
             """,nativeQuery = true)
    Optional<TeDetailClassRespone> findClassById(@Param("id") String id);

}
