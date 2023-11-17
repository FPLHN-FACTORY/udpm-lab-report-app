package com.labreportapp.labreport.core.student.repository;


import com.labreportapp.labreport.core.student.model.request.StFindMeetingRequest;
import com.labreportapp.labreport.core.student.model.response.StHomeWordAndNoteResponse;
import com.labreportapp.labreport.core.student.model.response.StMeetingResponse;
import com.labreportapp.labreport.core.student.model.response.StMyTeamInClassResponse;
import com.labreportapp.labreport.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author quynhncph26201
 */
@Repository
public interface StMeetingRepository extends JpaRepository<Meeting, String> {

    @Query(value = """
            SELECT  
            m.id as id,
            m.name as name,
            m.descriptions as descriptions,
            m.meeting_date as meeting_date,
            m.type_meeting as type_meeting,
            mp.name as meeting_period, mp.start_hour as start_hour, mp.start_minute as start_minute ,
            mp.end_hour as end_hour, mp.end_minute as end_minute,
            m.class_id as class_id
            FROM meeting m
            JOIN meeting_period mp ON mp.id = m.meeting_period
            JOIN class c ON c.id = m.class_id
            WHERE m.class_id = :#{#req.idClass}
            AND m.meeting_date < :#{#req.currentTime}
            ORDER BY m.meeting_date DESC
                     """, nativeQuery = true)
    List<StMeetingResponse> findMeetingByIdClass(@Param("req") StFindMeetingRequest req);

    Integer countMeetingByClassId(String idClass);

    @Query(value = """
            SELECT  m.id as id,
            m.name as name,
            m.descriptions as descriptions,
            m.meeting_date as meeting_date,
            m.type_meeting as type_meeting,
             mp.name as meeting_period, mp.start_hour as start_hour, mp.start_minute as start_minute ,
            mp.end_hour as end_hour, mp.end_minute as end_minute,
            m.class_id as class_id
            FROM meeting m
            JOIN meeting_period mp ON mp.id = m.meeting_period
            WHERE m.id = :#{#req.idMeeting}
                     """, nativeQuery = true)
    Optional<StMeetingResponse> searchMeetingByIdMeeting(@Param("req") StFindMeetingRequest req);

    @Query(value = """
            SELECT DISTINCT
                  m.id AS idMeeting,
                  m.name AS nameMeeting,
                  m.descriptions AS descriptionsMeeting,
                  h.id AS idHomeWork,
                  h.descriptions AS descriptionsHomeWork,
                  n.id AS idNote,
                  n.descriptions AS descriptionsNote,
                  r.id as idReport,
                  r.descriptions AS descriptionsReport
            FROM meeting m
            JOIN class c ON c.id = m.class_id
            JOIN team t ON t.class_id = c.id
            LEFT JOIN home_work h ON h.meeting_id = m.id AND h.team_id = :#{#req.idTeam}
            LEFT JOIN note n ON n.meeting_id = m.id AND n.team_id = :#{#req.idTeam}
            LEFT JOIN report r ON r.meeting_id = m.id AND r.team_id = :#{#req.idTeam}
            WHERE m.id = :#{#req.idMeeting}
                     """, nativeQuery = true)
    Optional<StHomeWordAndNoteResponse> searchDetailMeetingTeamByIdMeIdTeam(@Param("req") StFindMeetingRequest req);

    @Query(value = """
            SELECT a.id, a.name , a.subject_name , a.class_id FROM team a
            JOIN student_classes b ON b.team_id = a.id
             WHERE
             a.class_id = :#{#req.idClass} AND b.student_id = :#{#req.idStudent}
            """, nativeQuery = true)
    List<StMyTeamInClassResponse> getTeamInClass(@Param("req") StFindMeetingRequest req);

    @Query(value = """
            SELECT a.role FROM student_classes a
            WHERE a.student_id = :#{#req.idStudent} AND a.class_id = :#{#req.idClass}
            """, nativeQuery = true)
    Integer getRoleByIdStudent(@Param("req") StFindMeetingRequest req);

    @Query(value = """
            SELECT COUNT(m.class_id)
            FROM meeting m
            WHERE m.meeting_date <= :currentDate
                AND m.class_id = :classId
                      """, nativeQuery = true)
    Integer countMeetingLessonByIdClass(@Param("currentDate") Long currentDate, @Param("classId") String classId);

}
