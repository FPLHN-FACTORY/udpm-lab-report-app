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
            c.class_size, c.start_time, 
            mp.name as class_period, mp.start_hour as start_hour, mp.start_minute as start_minute ,
            mp.end_hour as end_hour, mp.end_minute as end_minute,
            g.name, ac.name as activityName, c.teacher_id AS idTeacher,
            s.start_time_student, s.end_time_student, c.descriptions,
            c.password
            FROM class c
            LEFT JOIN meeting_period mp ON mp.id = c.class_period
            JOIN activity ac ON c.activity_id = ac.id
            JOIN level g ON g.id = ac.level_id
            JOIN semester s ON ac.semester_id = s.id
            LEFT JOIN student_classes sc ON c.id = sc.class_id AND sc.student_id = :#{#req.studentId}
            WHERE :currentTime >= s.start_time_student and :currentTime <= s.end_time_student
            AND sc.class_id IS NULL
            AND (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR c.code LIKE %:#{#req.code}%) 
            AND (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR mp.id = :#{#req.classPeriod}) 
            AND (:#{#req.level} IS NULL OR :#{#req.level} LIKE '' OR g.id = :#{#req.level}) 
            AND (:#{#req.activityId} IS NULL OR :#{#req.activityId} LIKE '' OR ac.id = :#{#req.activityId}) 
            AND s.id = :#{#req.semesterId}
            ORDER BY c.created_date DESC
            """, countQuery = """
            SELECT COUNT(1)
            FROM class c
            LEFT JOIN meeting_period mp ON mp.id = c.class_period
            JOIN activity ac ON c.activity_id = ac.id
            JOIN level g ON g.id = ac.level_id
            JOIN semester s ON ac.semester_id = s.id
            LEFT JOIN student_classes sc ON c.id = sc.class_id AND sc.student_id = :#{#req.studentId}
            WHERE :currentTime >= s.start_time_student and :currentTime <= s.end_time_student
            AND sc.class_id IS NULL
            AND (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR c.code LIKE %:#{#req.code}%) 
            AND (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR mp.id = :#{#req.classPeriod}) 
            AND (:#{#req.level} IS NULL OR :#{#req.level} LIKE '' OR g.id = :#{#req.level}) 
            AND (:#{#req.activityId} IS NULL OR :#{#req.activityId} LIKE '' OR ac.id = :#{#req.activityId}) 
            AND s.id = :#{#req.semesterId}
            ORDER BY c.created_date DESC
            """, nativeQuery = true)
    Page<StClassResponse> getAllClassByCriteriaAndIsActive(@Param("req") StFindClassRequest req, Pageable pageable, @Param("currentTime") Long currentTime);

    @Query(value = """
            SELECT c.code
            FROM class c
            JOIN meeting_period mp ON mp.id = c.class_period
            JOIN activity ac ON c.activity_id = ac.id
            JOIN semester s ON ac.semester_id = s.id
            WHERE :currentTime >= s.start_time_student
            AND :currentTime <= s.end_time_student
            AND (:#{#req.idClass} IS NULL OR :#{#req.idClass} LIKE '' OR c.id = :#{#req.idClass}) 
            """, nativeQuery = true)
    Optional<StClassResponse> checkConditionCouldJoinOrLeaveClass(@Param("req") StClassRequest req, @Param("currentTime") Long currentTime);

    @Query(value = """
            SELECT a.id FROM student_classes a WHERE a.student_id = :idStudent AND a.class_id = :idClass
            """, nativeQuery = true)
    String checkStudentInClass(@Param("idStudent") String idStudent, @Param("idClass") String idClass);
}
