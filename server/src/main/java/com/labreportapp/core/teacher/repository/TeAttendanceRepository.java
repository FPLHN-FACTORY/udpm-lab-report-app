package com.labreportapp.core.teacher.repository;

import com.labreportapp.core.teacher.model.request.TeFindAttendanceRequest;
import com.labreportapp.core.teacher.model.response.TeAttendanceRespone;
import com.labreportapp.entity.Attendance;
import org.springframework.beans.factory.annotation.Value;
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
            SELECT a.id as id,
            a.name as name_meeting,
            a.status as status,
            a.student_id as student_id,
            a.meeting_id as meeting_id
            FROM attendance a
            WHERE a.meeting_id = :#{#req.meetingId} and a.student_id = :#{#req.studentId}
            """, nativeQuery = true)
    Optional<TeAttendanceRespone> findAttendanceByStudentIdAndMeetgId(@Param("req") TeFindAttendanceRequest req);

}
