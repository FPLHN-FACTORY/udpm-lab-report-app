package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.core.teacher.model.request.TeFindClassRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeClassResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeDetailClassRespone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.labreportapp.labreport.entity.Class;

import java.util.List;
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
            c.start_time as start_time,
            c.password as password,
            c.class_period as class_period,
            c.class_size as class_size,
            c.teacher_id as teacher_id,
            c.activity_id as activity_id,
            c.created_date as created_date,
            c.descriptions as descriptions,
            l.name as level,
            a.name as activity
            FROM activity a
            JOIN level l ON l.id = a.level_id
            JOIN class c ON c.activity_id = a.id
            JOIN semester s ON s.id = a.semester_id
            where c.teacher_id = :#{#req.idTeacher}
            and (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR :#{#req.idSemester} LIKE s.id)
            and (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR :#{#req.idActivity} LIKE a.id)
            and (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR :#{#req.code} = '_' AND c.code LIKE '%\\_%' ESCAPE '\\\\')
            and (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR  c.class_period = :#{#req.classPeriod})
            and (:#{#req.level} IS NULL OR :#{#req.level} LIKE '' OR l.id = :#{#req.level})
            ORDER BY c.last_modified_date DESC
                     """,countQuery = """
            SELECT COUNT(c.id) 
              FROM activity a
            JOIN level l ON l.id = a.level_id
            JOIN class c ON c.activity_id = a.id
            JOIN semester s ON s.id = a.semester_id
            where c.teacher_id = :#{#req.idTeacher}
            and (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR :#{#req.idSemester} LIKE s.id)
            and (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR :#{#req.idActivity} LIKE a.id)
            and (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR :#{#req.code} = '_' AND c.code LIKE '%\\_%' ESCAPE '\\\\')
            and (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR  c.class_period = :#{#req.classPeriod})
            and (:#{#req.level} IS NULL OR :#{#req.level} LIKE '' OR l.id = :#{#req.level})
            """ ,nativeQuery = true)
    Page<TeClassResponse> findClassBySemesterAndActivity(@Param("req") TeFindClassRequest req, Pageable pageable);

    @Query(value = """
            SELECT c.id as id,
            c.code as code,
            c.start_time as start_time,
            c.password as password,
            c.class_period as class_period,
            c.class_size as class_size,
            a.name as activityName,
            c.descriptions as descriptions,
            d.name as activityLevel,
            s.name as semesterName,
            c.status_class as status_class,
            a.allow_use_trello as allow_use_trello
            FROM activity a
            JOIN class c ON c.activity_id = a.id
            JOIN semester s ON s.id = a.semester_id
            JOIN level d ON d.id = a.level_id
            where c.id = :#{#id}
             """,nativeQuery = true)
    Optional<TeDetailClassRespone> findClassById(@Param("id") String id);

    @Query(value = """
            WITH LatestSemester AS (
                 SELECT id, start_time, end_time
                 FROM semester
                 WHERE UNIX_TIMESTAMP(NOW()) BETWEEN start_time / 1000 AND end_time / 1000
                 ORDER BY end_time DESC
                 LIMIT 1
                 )
            SELECT
            ROW_NUMBER() OVER(ORDER BY l.start_time DESC) AS stt,
            c.id AS id,
            c.code AS code,
            l.start_time as start_time,
            l.end_time as end_time,
            c.class_period as class_period,
            a.level as level
            FROM class c
            JOIN activity a ON a.id = c.activity_id
            JOIN LatestSemester l ON l.id = a.semester_id
            WHERE c.teacher_id = :#{#id}
             """,nativeQuery = true)
    List<TeClassResponse> getClassClosestToTheDateToSemester(@Param("id") String id);

}
