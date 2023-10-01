package com.labreportapp.labreport.core.student.repository;

import com.labreportapp.labreport.core.student.model.request.StFindClassRequest;
import com.labreportapp.labreport.core.student.model.response.StCheckFeedBackResponse;
import com.labreportapp.labreport.core.student.model.response.StMyClassResponse;
import com.labreportapp.labreport.repository.FeedBackRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface StFeedBackRepository extends FeedBackRepository {

    @Query(value = """
            SELECT a.id, a.class_id, a.student_id, a.email, a.role, a.status_student_feed_back
            FROM student_classes a JOIN class b ON a.class_id = b.id
            JOIN activity c ON b.activity_id = c.id 
            WHERE a.student_id = :idStudent AND c.semester_id = :idSemester
            """, nativeQuery = true)
    List<StCheckFeedBackResponse> getStudentClassesByIdStudent(@Param("idStudent") String idStudent, @Param("idSemester") String idSemester);

    @Query(value = """
            SELECT a.id, ROW_NUMBER() OVER(ORDER BY c.created_date DESC) AS stt,
            a.code, a.start_time, a.class_period, a.teacher_id, e.name, b.name AS nameActivity
            FROM class a JOIN activity b ON a.activity_id = b.id 
            JOIN semester d ON b.semester_id = d.id
            JOIN student_classes c ON a.id = c.class_id
            JOIN level e ON e.id = b.level_id
            WHERE (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR a.code LIKE %:#{#req.code}%) 
            AND (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR a.class_period = :#{#req.classPeriod}) 
            AND (:#{#req.level} IS NULL OR :#{#req.level} LIKE '' OR e.id = :#{#req.level}) 
            AND (:#{#req.activityId} IS NULL OR :#{#req.activityId} LIKE '' OR b.id = :#{#req.activityId}) 
            AND d.id = :#{#req.semesterId}
            AND c.student_id = :#{#req.studentId}
            AND c.status_student_feed_back = 0
            ORDER BY c.created_date DESC
            """, nativeQuery = true)
    List<StMyClassResponse> getAllClass(@Param("req") StFindClassRequest req);

}
