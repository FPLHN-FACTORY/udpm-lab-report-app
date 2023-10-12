package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.response.AdMeetingResponse;
import com.labreportapp.labreport.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author thangncph26123
 */
@Repository
public interface AdMeetingRepository extends JpaRepository<Meeting, String> {

    @Query(value = """
            WITH MeetingAttendance AS (
                SELECT COUNT(CASE WHEN b.status = 0 THEN b.id ELSE NULL END) AS so_diem_danh, a.id
                FROM meeting a JOIN attendance b ON a.id = b.meeting_id
                WHERE a.class_id = :idClass 
                GROUP BY a.id
            )
            SELECT a.id, a.name, a.meeting_date, mp.name,
            a.type_meeting, a.address, a.teacher_id, a.descriptions, 
            meetingAtten.so_diem_danh
            FROM meeting a
              JOIN meeting_period mp ON mp.id = a.meeting_period
             LEFT JOIN class b ON a.class_id = b.id
            LEFT JOIN MeetingAttendance meetingAtten ON meetingAtten.id = a.id
            WHERE b.id = :idClass ORDER BY a.meeting_date DESC, mp.name DESC
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

    @Query(value = """
            SELECT COUNT(1) FROM meeting a WHERE a.class_id = :idClass
            """, nativeQuery = true)
    Integer countMeetingByIdClass(@Param("idClass") String idClass);

    @Modifying
    @Transactional
    @Query(value = """
              UPDATE meeting a
              JOIN (
                  SELECT m.id, ROW_NUMBER() OVER (ORDER BY m.meeting_date ASC, mp.name ASC) AS row_num
                  FROM meeting m
                   JOIN meeting_period mp ON mp.id = m.meeting_period
                  WHERE class_id = :idClass
              ) AS subquery
              ON a.id = subquery.id
              SET a.name = CONCAT('Buổi ', subquery.row_num);
            """, nativeQuery = true)
    void updateNameMeeting(@Param("idClass") String idClass);
}
