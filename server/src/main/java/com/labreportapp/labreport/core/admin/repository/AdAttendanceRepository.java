package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.response.AdAttendanceMeetingResponse;
import com.labreportapp.labreport.repository.AttendanceRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface AdAttendanceRepository extends AttendanceRepository {

    @Query(value = """
            SELECT a.id, a.student_id, a.meeting_id, a.status FROM attendance a JOIN meeting b ON a.meeting_id = b.id
            JOIN class c ON c.id = b.class_id
            WHERE b.id = :idMeeting AND c.id = :idClass
            """, nativeQuery = true)
    List<AdAttendanceMeetingResponse> getAttendanceByIdMeetingAndIdClass(@Param("idMeeting") String idMeeting, @Param("idClass") String idClass);
}
