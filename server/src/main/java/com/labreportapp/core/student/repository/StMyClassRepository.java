package com.labreportapp.core.student.repository;

import com.labreportapp.core.student.model.request.StFindClassRequest;
import com.labreportapp.core.student.model.response.StMyClassResponse;
import com.labreportapp.repository.ClassRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thangncph26123
 */
@Repository
public interface StMyClassRepository extends ClassRepository {

    @Query(value = """
            SELECT a.id, ROW_NUMBER() OVER(ORDER BY c.created_date DESC) AS stt,
            a.code, a.name, a.start_time, a.class_period, a.teacher_id, b.level
            FROM class a JOIN activity b ON a.activity_id = b.id 
            JOIN student_classes c ON a.id = c.class_id
            WHERE (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR a.code LIKE %:#{#req.code}%) 
            AND (:#{#req.name} IS NULL OR :#{#req.name} LIKE '' OR a.name LIKE %:#{#req.name}%) 
            AND (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR a.class_period = :#{#req.classPeriod}) 
            AND (:#{#req.level} IS NULL OR :#{#req.level} LIKE '' OR b.level = :#{#req.level}) 
            AND (:#{#req.activityId} IS NULL OR :#{#req.activityId} LIKE '' OR b.id = :#{#req.activityId}) 
            AND c.student_id = :#{#req.studentId}
            ORDER BY c.created_date DESC
            """, nativeQuery = true)
    List<StMyClassResponse> getAllClass(@Param("req") StFindClassRequest req);
}
