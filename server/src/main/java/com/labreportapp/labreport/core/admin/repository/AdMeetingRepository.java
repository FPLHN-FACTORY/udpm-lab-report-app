package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.response.AdMeetingResponse;
import com.labreportapp.labreport.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thangncph26123
 */
@Repository
public interface AdMeetingRepository extends JpaRepository<Meeting, String> {

    @Query(value = """
            SELECT a.id, a.name, a.meeting_date, a.meeting_period,
            a.type_meeting, a.address, a.descriptions
            FROM meeting a LEFT JOIN class b ON a.class_id = b.id
            WHERE b.id = :idClass ORDER BY a.created_date DESC
            """, nativeQuery = true)
    List<AdMeetingResponse> getAllMeetingByIdClass(@Param("idClass") String idClass);

    @Query(value = """
            SELECT a.id FROM attendance a WHERE a.meeting_id = :idMeeting
            """, nativeQuery = true)
    List<String> findAttendanceByIdMeeting(@Param("idMeeting") String idMeeting);

    @Query(value = """
            SELECT a.id FROM note a WHERE a.meeting_id = :idMeeting
            """, nativeQuery = true)
    List<String> findNoteByIdMeeting(@Param("idMeeting") String idMeeting);

    @Query(value = """
            SELECT a.id FROM home_work a WHERE a.meeting_id = :idMeeting
            """, nativeQuery = true)
    List<String> findHomeWorkByIdMeeting(@Param("idMeeting") String idMeeting);
}
