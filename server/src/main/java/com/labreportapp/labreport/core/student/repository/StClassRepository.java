package com.labreportapp.labreport.core.student.repository;

import com.labreportapp.labreport.core.student.model.request.StClassRequest;
import com.labreportapp.labreport.core.student.model.request.StFindClassRequest;
import com.labreportapp.labreport.core.student.model.response.StClassResponse;
import com.labreportapp.labreport.repository.ClassRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StClassRepository extends ClassRepository {

    @Query(value = """
            SELECT c.id, ROW_NUMBER() OVER(ORDER BY c.created_date DESC) as stt, c.code,
            c.class_size, c.start_time, c.class_period, g.name, ac.name as activityName,
            s.start_time_student, s.end_time_student, c.descriptions
            FROM class c
            JOIN activity ac ON c.activity_id = ac.id
            JOIN level g ON g.id = ac.level_id
            JOIN semester s ON ac.semester_id = s.id
            WHERE curdate() >= FROM_UNIXTIME(s.start_time_student / 1000) and curdate() <= FROM_UNIXTIME(s.end_time_student / 1000)
            AND (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR c.code LIKE %:#{#req.code}%) 
            AND (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR c.class_period = :#{#req.classPeriod}) 
            AND (:#{#req.level} IS NULL OR :#{#req.level} LIKE '' OR g.id = :#{#req.level}) 
            AND (:#{#req.activityId} IS NULL OR :#{#req.activityId} LIKE '' OR ac.id = :#{#req.activityId}) 
            AND s.id = :#{#req.semesterId}
            ORDER BY c.created_date DESC
            """, countQuery = """
              SELECT COUNT(1)
              FROM class c
              JOIN activity ac ON c.activity_id = ac.id
              JOIN level g ON g.id = ac.level_id
              JOIN semester s ON ac.semester_id = s.id
              WHERE curdate() >= FROM_UNIXTIME(s.start_time_student / 1000) and curdate() <= FROM_UNIXTIME(s.end_time_student / 1000)
              AND (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR c.code LIKE %:#{#req.code}%) 
              AND (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR c.class_period = :#{#req.classPeriod}) 
              AND (:#{#req.level} IS NULL OR :#{#req.level} LIKE '' OR g.id = :#{#req.level}) 
              AND (:#{#req.activityId} IS NULL OR :#{#req.activityId} LIKE '' OR ac.id = :#{#req.activityId}) 
              AND s.id = :#{#req.semesterId}
              ORDER BY c.created_date DESC
            """, nativeQuery = true)
    Page<StClassResponse> getAllClassByCriteriaAndIsActive(@Param("req") StFindClassRequest req, Pageable pageable);

    @Query(value = """
            SELECT c.code
            FROM class c
            JOIN activity ac ON c.activity_id = ac.id
            JOIN semester s ON ac.semester_id = s.id
            WHERE curdate() >= FROM_UNIXTIME(s.start_time_student / 1000)
            AND curdate() <= FROM_UNIXTIME(s.end_time_student / 1000)
            AND (:#{#req.idClass} IS NULL OR :#{#req.idClass} LIKE '' OR c.id = :#{#req.idClass}) 
            """, nativeQuery = true)
    Optional<StClassResponse> checkConditionCouldJoinOrLeaveClass(@Param("req") StClassRequest req);
}
