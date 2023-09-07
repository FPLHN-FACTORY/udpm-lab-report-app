package com.labreportapp.core.student.repository;

import com.labreportapp.core.student.model.request.FindTeamByIdClass;
import com.labreportapp.core.student.model.request.StFindMeetingRequest;
import com.labreportapp.core.student.model.response.StHomeWordAndNoteResponse;
import com.labreportapp.core.student.model.response.StMeetingResponse;
import com.labreportapp.core.student.model.response.StMyTeamInClassResponse;
import com.labreportapp.entity.Meeting;
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
public interface StMeetingRepository extends JpaRepository<Meeting , String> {
    @Query(value = """
            SELECT  
            m.id as id,
            m.name as name,
            m.descriptions as descriptions,
            m.meeting_date as meeting_date,
            m.type_meeting as type_meeting,
            m.meeting_period as meeting_period,
            m.class_id as class_id
            FROM meeting m
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
            m.meeting_period as meeting_period,
             m.class_id as class_id
            FROM meeting m
            WHERE m.id = :#{#req.idMeeting}
                     """, nativeQuery = true)
    Optional<StMeetingResponse> searchMeetingByIdMeeting(@Param("req") StFindMeetingRequest req);

    @Query(value = """
            SELECT m.id AS idMeeting,
            m.name AS nameMeeting,
            m.descriptions AS descriptionsMeeting,
            m.created_date AS createdDate,
            h.id AS idHomeWork,
            h.descriptions AS descriptionsHomeWork,
            n.id AS idNote,
            n.descriptions AS descriptionsNote
             FROM meeting m
             JOIN home_work h ON h.meeting_id = m.id
             JOIN note n ON n.meeting_id = m.id
             WHERE m.id = :#{#req.idMeeting} and h.team_id = :#{#req.idTeam} and n.team_id= :#{#req.idTeam}
                      """, nativeQuery = true)
    Optional<StHomeWordAndNoteResponse> searchDetailMeetingTeamByIdMeIdTeam(@Param("req") StFindMeetingRequest req);

    @Query(value = """
            SELECT a.id, a.code , a.name , a.subject_name , a.class_id FROM team a
            JOIN student_classes b ON b.team_id = a.id
             WHERE
             a.class_id = :#{#req.idClass} AND b.student_id = :#{#req.idStudent}
            """, nativeQuery = true)
    List<StMyTeamInClassResponse> getTeamInClass(@Param("req") StFindMeetingRequest req);
}
