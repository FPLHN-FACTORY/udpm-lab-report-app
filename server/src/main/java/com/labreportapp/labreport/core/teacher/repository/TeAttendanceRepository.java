package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.core.teacher.model.request.TeFindAttendanceRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentAttendanceRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentAttendanceRespone;
import com.labreportapp.labreport.entity.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author hieundph25894
 */
@Repository
public interface TeAttendanceRepository extends JpaRepository<Attendance, String> {

    @Query(value = """
            SELECT a.id as idAttendance,
            a.name as name_meeting,
            a.status as status,
            a.notes as notes,
            a.student_id as student_id,
            a.meeting_id as meeting_id,
            m.meeting_date as meeting_date
            FROM attendance a
            JOIN meeting m ON m.id = a.meeting_id
            WHERE a.meeting_id = :#{#idMeeting}
            """, nativeQuery = true)
    List<TeAttendanceResponse> findListAttendanceByIdMeeting(@Param("idMeeting") String idMeeting);

    @Query(value = """
            SELECT a.id as idAttendance,
            a.name as name_meeting,
            a.status as status,
            a.notes as notes,
            a.student_id as student_id,
            a.meeting_id as meeting_id,
            m.meeting_date as meeting_date
            FROM attendance a
            JOIN meeting m ON m.id = a.meeting_id
            WHERE a.meeting_id = :#{#req.idMeeting} and a.student_id = :#{#req.idStudent}
            """, nativeQuery = true)
    Optional<TeAttendanceResponse> findAttendanceByStudentIdAndMeetgId(@Param("req") TeFindAttendanceRequest req);

    @Query(value = """
            SELECT a.id as idAttendance,
            a.name as name_meeting,
            a.status as status,
            a.notes as notes,
            a.student_id as student_id,
            a.meeting_id as meeting_id,
            m.meeting_date as meeting_date
            FROM attendance a
            RIGHT JOIN meeting m ON m.id = a.meeting_id
            JOIN class c on c.id = m.class_id
            WHERE c.id = :#{#idClass}
            """, nativeQuery = true)
    List<TeAttendanceResponse> findAttendanceByIdClass(@Param("idClass") String idClass);

    @Query(value = """                        
            SELECT DISTINCT m.id AS id, 
                    ROW_NUMBER() OVER(ORDER BY m.meeting_date ASC) AS stt,
                    m.name AS name, 
                    m.meeting_date AS meeting_date,
                    m.meeting_period AS meeting_period, 
                    m.type_meeting AS type_meeting, 
                    m.teacher_id AS teacher_id,
                    a.status AS status,
                    a.notes AS notes
            FROM attendance a
            RIGHT JOIN meeting m ON a.meeting_id = m.id
            JOIN class c ON m.class_id = c.id
            JOIN student_classes st ON c.id = st.class_id
            JOIN activity ac ON c.activity_id = ac.id
            JOIN semester s ON ac.semester_id = s.id
            WHERE (a.student_id IS NULL OR a.student_id = :#{#req.idStudent})
            AND st.class_id = :#{#req.idClass}
            GROUP BY m.id,m.name, m.meeting_date, m.meeting_period, m.type_meeting, m.teacher_id,
             a.status, a.notes
            ORDER BY m.meeting_date ASC 
              """, countQuery = """
             SELECT COUNT(DISTINCT m.id)
                        FROM attendance a
                        RIGHT JOIN meeting m ON a.meeting_id = m.id
                        JOIN class c ON m.class_id = c.id
                        JOIN student_classes st ON c.id = st.class_id
                        JOIN activity ac ON c.activity_id = ac.id
                        JOIN semester s ON ac.semester_id = s.id
                        WHERE (a.student_id IS NULL OR a.student_id = :#{#req.idStudent})
                        AND st.class_id = :#{#req.idClass}
                        GROUP BY m.id,m.name, m.meeting_date, m.meeting_period, m.type_meeting, m.teacher_id, 
                        a.status, a.notes
            """, nativeQuery = true)
    Page<TeStudentAttendanceRespone> getAllStudentAttendanceById(@Param("req") TeFindStudentAttendanceRequest req, Pageable pageable);
}
