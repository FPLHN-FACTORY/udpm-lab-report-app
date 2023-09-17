package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindScheduleMeetingClassRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeHomeWorkAndNoteMeetingRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingCustomToAttendanceRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeScheduleMeetingClassRespone;
import com.labreportapp.labreport.entity.Meeting;
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
public interface TeMeetingRepository extends JpaRepository<Meeting, String> {

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
            ORDER BY m.meeting_date DESC
                     """, nativeQuery = true)
    List<TeMeetingRespone> findMeetingByIdClass(@Param("req") TeFindMeetingRequest req);

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
    Optional<TeMeetingRespone> searchMeetingByIdMeeting(@Param("req") TeFindMeetingRequest req);

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
    Optional<TeHomeWorkAndNoteMeetingRespone> searchDetailMeetingTeamByIdMeIdTeam(@Param("req") TeFindMeetingRequest req);

    @Query(value = """
            SELECT 
             c.id as id_class,
             c.code as code_class,
             m.id as id_meeting,
             m.meeting_date as meeting_date,
             m.meeting_period as meeting_period,
             m.name as name_meeting,
             m.type_meeting as type_meeting,
             m.address as address_meeting,
             m.descriptions as descriptions_meeting
             FROM class c
             JOIN meeting m ON m.class_id = c.id
             WHERE c.teacher_id = :#{#req.idTeacher} AND DATE(FROM_UNIXTIME(m.meeting_date / 1000)) = CURDATE()
             ORDER BY m.meeting_date ASC
            """, nativeQuery = true)
    List<TeScheduleMeetingClassRespone> searchScheduleToDayByIdTeacherAndMeetingDate(@Param("req") TeFindScheduleMeetingClassRequest req);

    @Query(value = """
            SELECT  
            m.id as id,
            m.name as name,
            m.class_id as class_id,
            m.meeting_date as meeting_date
            FROM meeting m
            JOIN class c ON c.id = m.class_id
            WHERE m.class_id = :#{#idClass}
            ORDER BY m.meeting_date ASC
                     """, nativeQuery = true)
    List<TeMeetingCustomToAttendanceRespone> findMeetingCustomToAttendanceByIdClass(@Param("idClass") String idClass);

}