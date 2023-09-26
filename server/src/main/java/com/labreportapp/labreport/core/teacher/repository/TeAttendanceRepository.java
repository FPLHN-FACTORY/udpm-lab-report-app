package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.core.teacher.model.request.TeFindAttendanceRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceResponse;
import com.labreportapp.labreport.entity.Attendance;
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

}
