package com.labreportapp.core.student.repository;

import com.labreportapp.core.student.model.request.FindTeamByIdClass;
import com.labreportapp.core.student.model.request.FindTeamClassRequest;
import com.labreportapp.core.student.model.request.StClassRequest;
import com.labreportapp.core.student.model.request.StFindClassRequest;
import com.labreportapp.core.student.model.response.StClassResponse;
import com.labreportapp.core.student.model.response.StMyClassResponse;
import com.labreportapp.core.student.model.response.StMyStudentTeamResponse;
import com.labreportapp.core.student.model.response.StMyTeamInClassResponse;
import com.labreportapp.entity.StudentClasses;
import com.labreportapp.repository.ClassRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author thangncph26123
 */
@Repository
public interface StMyClassRepository extends ClassRepository {

    @Query(value = """
            SELECT a.id, ROW_NUMBER() OVER(ORDER BY c.created_date DESC) AS stt,
            a.code, a.start_time, a.class_period, a.teacher_id, b.level
            FROM class a JOIN activity b ON a.activity_id = b.id 
            JOIN semester d ON b.semester_id = d.id
            JOIN student_classes c ON a.id = c.class_id
            WHERE (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR a.code LIKE %:#{#req.code}%) 
            AND (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR a.class_period = :#{#req.classPeriod}) 
            AND (:#{#req.level} IS NULL OR :#{#req.level} LIKE '' OR b.level = :#{#req.level}) 
            AND (:#{#req.activityId} IS NULL OR :#{#req.activityId} LIKE '' OR b.id = :#{#req.activityId}) 
            AND d.id = :#{#req.semesterId}
            AND c.student_id = :#{#req.studentId}
            ORDER BY c.created_date DESC
            """, nativeQuery = true)
    List<StMyClassResponse> getAllClass(@Param("req") StFindClassRequest req);

    @Query(value = """
            SELECT a.id, a.code , a.name , a.subject_name , a.class_id FROM team a
             WHERE
             (:#{#req.idClass} IS NULL
             OR :#{#req.idClass} = ''
             OR a.class_id = :#{#req.idClass})
            """, nativeQuery = true)
    List<StMyTeamInClassResponse> getTeamInClass(@Param("req") FindTeamByIdClass req);

    @Query(value = """
            SELECT a.id, a.student_id , a.class_id , a.team_id , a.email , a.role ,a.status FROM student_classes a
            JOIN team b ON b.id = a.team_id
            WHERE a.class_id = :#{#req.idClass} AND a.team_id = :#{#req.idTeam}
            """, nativeQuery = true)
    List<StMyStudentTeamResponse> getStudentInMyTeam(@Param("req") FindTeamClassRequest req);

    @Query(value = """
            SELECT a.team_id FROM student_classes a WHERE a.class_id = :idClass AND student_id = :idStudent
            """, nativeQuery = true)
    String checkStatusStudentInClass(@Param("idClass") String idClass, @Param("idStudent") String idStudent);

    @Query(value = """
            SELECT a.* FROM student_classes a WHERE a.class_id = :idClass AND a.student_id = :idStudent
            """, nativeQuery = true)
    StudentClasses findStudentClasses(@Param("idClass") String idClass, @Param("idStudent") String idStudent);

    @Query(value = """
            SELECT COUNT(a.id) FROM student_classes a WHERE a.class_id = :idClass AND a.team_id = :idTeam
            """, nativeQuery = true)
    Integer countMemberInTeam(@Param("idClass") String idClass, @Param("idTeam") String idTeam);

    @Query(value = """
            SELECT c.id, c.code, c.class_size, c.class_period,
            m.name AS meeting_name, m.meeting_date
            FROM class c
            JOIN (SELECT m.class_id, m.name, m.meeting_date
            FROM meeting m
            WHERE m.meeting_date IN (SELECT MIN(meeting_date)
            FROM meeting
            GROUP BY class_id)) m ON m.class_id = c.id
            WHERE DATE(FROM_UNIXTIME(m.meeting_date / 1000)) > CURDATE()
            AND c.id = :#{#req.idClass}
            """, nativeQuery = true)
    StClassResponse checkTheFirstMeetingDateByClass(final StClassRequest req);
}
