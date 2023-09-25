package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindScheduleMeetingClassRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindScheduleNowToTime;
import com.labreportapp.labreport.core.teacher.model.response.TeHomeWorkAndNoteMeetingRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingCustomToAttendanceRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeScheduleMeetingClassRespone;
import com.labreportapp.labreport.entity.Meeting;
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
public interface TeMeetingRepository extends JpaRepository<Meeting, String> {

    @Query(value = """
            SELECT  
            m.id as id,
            m.name as name,
            m.descriptions as descriptions,
            m.meeting_date as meeting_date,
            m.type_meeting as type_meeting,
            m.meeting_period as meeting_period,
            m.class_id as class_id,
            m.teacher_id as teacher_id
            FROM meeting m
            JOIN class c ON c.id = m.class_id
            WHERE m.class_id = :#{#req.idClass}
            ORDER BY m.meeting_date DESC
                     """, nativeQuery = true)
    List<TeMeetingRespone> findMeetingByIdClass(@Param("req") TeFindMeetingRequest req);

    @Query(value = """
            SELECT  m.id as id,
            m.name as name,
            m.descriptions as descriptions,
            m.meeting_date as meeting_date,
            m.type_meeting as type_meeting,
            m.meeting_period as meeting_period,
            m.class_id as class_id,
            m.teacher_id as teacher_id
            FROM meeting m
            WHERE m.id = :#{#req.idMeeting}
                     """, nativeQuery = true)
    Optional<TeMeetingRespone> searchMeetingByIdMeeting(@Param("req") TeFindMeetingRequest req);

    @Query(value = """
            SELECT 
            m.id AS idMeeting,
            m.name AS nameMeeting,
            m.descriptions AS descriptionsMeeting,
            m.created_date AS createdDate,
            h.id AS idHomeWork,
            h.descriptions AS descriptionsHomeWork,
            n.id AS idNote,
            n.descriptions AS descriptionsNote,
            r.id as idReport,
            r.descriptions AS descriptionsReport
            FROM meeting m
             JOIN home_work h ON h.meeting_id = m.id AND h.team_id = :#{#req.idTeam}
             JOIN note n ON n.meeting_id = m.id AND n.team_id = :#{#req.idTeam}
             JOIN report r ON r.meeting_id = m.id AND r.team_id = :#{#req.idTeam}
            WHERE m.id = :#{#req.idMeeting}
                      """, nativeQuery = true)
    Optional<TeHomeWorkAndNoteMeetingRespone> searchDetailMeetingTeamByIdMeIdTeam(@Param("req") TeFindMeetingRequest req);

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
             m.descriptions as descriptions_meeting,
             l.name as level
             FROM class c
             JOIN meeting m ON m.class_id = c.id
             JOIN activity ac ON ac.id = c.activity_id
             JOIN level l on l.id = ac.level_id
             WHERE c.teacher_id = :#{#req.idTeacher} AND DATE(FROM_UNIXTIME(m.meeting_date / 1000)) = CURDATE()
             ORDER BY m.meeting_date ASC
            """, nativeQuery = true)
    List<TeScheduleMeetingClassRespone> searchScheduleToDayByIdTeacherAndMeetingDate(@Param("req") TeFindScheduleMeetingClassRequest req);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY m.meeting_date ASC) AS stt,
             c.id as id_class,
             c.code as code_class,
             m.id as id_meeting,
             m.meeting_date as meeting_date,
             m.meeting_period as meeting_period,
             m.name as name_meeting,
             m.type_meeting as type_meeting,
             m.address as address_meeting,
             m.descriptions as descriptions_meeting,
             l.name as level
             FROM class c
             JOIN meeting m ON m.class_id = c.id
             JOIN activity ac ON ac.id = c.activity_id
             JOIN level l on l.id = ac.level_id
             WHERE c.teacher_id = :#{#req.idTeacher} AND 
             (
                     (:#{#req.time} LIKE '-7' AND m.meeting_date BETWEEN
                         UNIX_TIMESTAMP(DATE_SUB(CURRENT_DATE(), INTERVAL 7 DAY)) * 1000
                         AND UNIX_TIMESTAMP(CURRENT_DATE()) * 1000)
                     OR
                      (:#{#req.time} LIKE '-14' AND m.meeting_date BETWEEN
                         UNIX_TIMESTAMP(DATE_SUB(CURRENT_DATE(), INTERVAL 14 DAY)) * 1000
                         AND UNIX_TIMESTAMP(CURRENT_DATE()) * 1000)
                          OR
                      (:#{#req.time} LIKE '-30' AND m.meeting_date BETWEEN
                         UNIX_TIMESTAMP(DATE_SUB(CURRENT_DATE(), INTERVAL 30 DAY)) * 1000
                         AND UNIX_TIMESTAMP(CURRENT_DATE()) * 1000)
                     OR
                     (:#{#req.time} LIKE '7' AND m.meeting_date BETWEEN
                         UNIX_TIMESTAMP(CURRENT_DATE()) * 1000
                         AND UNIX_TIMESTAMP(DATE_ADD(CURRENT_DATE(), INTERVAL 7 DAY)) * 1000)
                          OR
                     (:#{#req.time} LIKE '14' AND m.meeting_date BETWEEN
                         UNIX_TIMESTAMP(CURRENT_DATE()) * 1000
                         AND UNIX_TIMESTAMP(DATE_ADD(CURRENT_DATE(), INTERVAL 14 DAY)) * 1000)
                          OR
                     (:#{#req.time} LIKE '30' AND m.meeting_date BETWEEN
                         UNIX_TIMESTAMP(CURRENT_DATE()) * 1000
                         AND UNIX_TIMESTAMP(DATE_ADD(CURRENT_DATE(), INTERVAL 30 DAY)) * 1000)
                 )
             ORDER BY m.meeting_date ASC
            """, countQuery = """
              SELECT COUNT(m.id)
                         FROM class c
                         JOIN meeting m ON m.class_id = c.id
                         JOIN activity ac ON ac.id = c.activity_id
                         JOIN level l on l.id = ac.level_id
                         WHERE c.teacher_id = :#{#req.idTeacher} AND 
                         (
                                 (:#{#req.time} LIKE '-7' AND m.meeting_date BETWEEN
                                     UNIX_TIMESTAMP(DATE_SUB(CURRENT_DATE(), INTERVAL 7 DAY)) * 1000
                                     AND UNIX_TIMESTAMP(CURRENT_DATE()) * 1000)
                                 OR
                                  (:#{#req.time} LIKE '-14' AND m.meeting_date BETWEEN
                                     UNIX_TIMESTAMP(DATE_SUB(CURRENT_DATE(), INTERVAL 14 DAY)) * 1000
                                     AND UNIX_TIMESTAMP(CURRENT_DATE()) * 1000)
                                      OR
                                  (:#{#req.time} LIKE '-30' AND m.meeting_date BETWEEN
                                     UNIX_TIMESTAMP(DATE_SUB(CURRENT_DATE(), INTERVAL 30 DAY)) * 1000
                                     AND UNIX_TIMESTAMP(CURRENT_DATE()) * 1000)
                                 OR
                                 (:#{#req.time} LIKE '7' AND m.meeting_date BETWEEN
                                     UNIX_TIMESTAMP(CURRENT_DATE()) * 1000
                                     AND UNIX_TIMESTAMP(DATE_ADD(CURRENT_DATE(), INTERVAL 7 DAY)) * 1000)
                                      OR
                                 (:#{#req.time} LIKE '14' AND m.meeting_date BETWEEN
                                     UNIX_TIMESTAMP(CURRENT_DATE()) * 1000
                                     AND UNIX_TIMESTAMP(DATE_ADD(CURRENT_DATE(), INTERVAL 14 DAY)) * 1000)
                                      OR
                                 (:#{#req.time} LIKE '30' AND m.meeting_date BETWEEN
                                     UNIX_TIMESTAMP(CURRENT_DATE()) * 1000
                                     AND UNIX_TIMESTAMP(DATE_ADD(CURRENT_DATE(), INTERVAL 30 DAY)) * 1000)
                             )
            """, nativeQuery = true)
    Page<TeScheduleMeetingClassRespone> searchScheduleNowToTimeByIdTeacher(@Param("req") TeFindScheduleNowToTime req, Pageable pageable);

    Optional<Meeting> findMeetingById(String id);

}