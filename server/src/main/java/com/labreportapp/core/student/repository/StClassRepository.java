package com.labreportapp.core.student.repository;

import com.labreportapp.core.student.model.request.StFindClassRequest;
import com.labreportapp.core.student.model.response.StClassResponse;
import com.labreportapp.repository.ClassRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StClassRepository extends ClassRepository {

  @Query(value = """
          SELECT c.id, ROW_NUMBER() OVER(ORDER BY c.created_date DESC) as stt, c.code,
          c.teacher_id, c.class_size, c.start_time, c.class_period, ac.level
          FROM class c
          JOIN activity ac ON c.activity_id = ac.id
          JOIN semester s ON ac.semester_id = s.id
          LEFT JOIN (SELECT m.class_id, m.name, m.meeting_date
          FROM meeting m RIGHT JOIN class cl ON cl.id = m.class_id
          WHERE m.meeting_date IN (SELECT MIN(meeting_date)
          FROM meeting
          GROUP BY class_id)) 
          m ON m.class_id = c.id
          WHERE (m.name IS NULL OR DATE(FROM_UNIXTIME(m.meeting_date / 1000)) > CURDATE())
          AND (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR c.code LIKE %:#{#req.code}%) 
          AND (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR c.class_period = :#{#req.classPeriod}) 
          AND (:#{#req.level} IS NULL OR :#{#req.level} LIKE '' OR ac.level = :#{#req.level}) 
          AND (:#{#req.activityId} IS NULL OR :#{#req.activityId} LIKE '' OR ac.id = :#{#req.activityId}) 
          AND s.id = :#{#req.semesterId}
          ORDER BY c.created_date DESC
          """, nativeQuery = true)
  List<StClassResponse> getAllClassByCriteriaAndIsActive(@Param("req") StFindClassRequest req);

}
