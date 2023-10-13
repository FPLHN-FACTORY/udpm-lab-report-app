package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindScheduleMeetingClassRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindScheduleNowToTime;
import com.labreportapp.labreport.core.teacher.model.response.TeDetailTeamReportRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeHomeWorkAndNoteMeetingResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingCustomToAttendanceResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeScheduleMeetingClassResponse;
import com.labreportapp.labreport.entity.Meeting;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
                mp.name as meeting_period, mp.start_hour as start_hour, mp.start_minute as start_minute ,
                mp.end_hour as end_hour, mp.end_minute as end_minute,
                m.class_id as class_id,
                m.teacher_id as teacher_id,
                m.status_meeting as status_meeting
            FROM meeting m
            JOIN meeting_period mp ON mp.id = m.meeting_period
            JOIN class c ON c.id = m.class_id
            WHERE m.class_id = :#{#req.idClass}
            ORDER BY m.meeting_date DESC
                     """, nativeQuery = true)
    List<TeMeetingResponse> findMeetingByIdClass(@Param("req") TeFindMeetingRequest req);

    @Query(value = """
            SELECT  m.id as id,
                m.name as name,
                m.descriptions as descriptions,
                m.meeting_date as meeting_date,
                mp.name as meeting_period, mp.start_hour as start_hour, mp.start_minute as start_minute ,
                mp.end_hour as end_hour, mp.end_minute as end_minute,
                m.type_meeting as type_meeting,
                mp.name as meeting_period,
                m.notes as notes,
                m.class_id as class_id,
                m.teacher_id as teacher_id,
                 m.status_meeting as status_meeting
            FROM meeting m
            JOIN meeting_period mp ON mp.id = m.meeting_period
            WHERE m.id = :#{#req.idMeeting}
                     """, nativeQuery = true)
    Optional<TeMeetingResponse> searchMeetingByIdMeeting(@Param("req") TeFindMeetingRequest req);

    @Query(value = """
            SELECT DISTINCT
                t.id AS id_team,
                t.name AS name_team,
                r.id AS id_report
            FROM team t
            JOIN class c ON c.id = t.class_id
            JOIN meeting m ON m.class_id = c.id
            LEFT JOIN report r ON r.team_id = t.id AND r.meeting_id = :#{#req.idMeeting}
            WHERE t.class_id = :#{#req.idClass}
                AND (r.id IS NULL OR r.id = '') 
            ORDER BY t.name ASC
                """, nativeQuery = true)
    List<TeDetailTeamReportRespone> findTeamReportByIdMeetingIdClass(@Param("req") TeFindMeetingRequest req);

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
                r.descriptions AS descriptionsReport,
                m.meeting_date as meeting_date,
                mp.name as meeting_period
            FROM meeting m
             JOIN meeting_period mp ON mp.id = m.meeting_period
            JOIN class c ON c.id = m.class_id
            JOIN team t ON t.class_id = c.id
            LEFT JOIN home_work h ON h.meeting_id = m.id AND h.team_id = :#{#req.idTeam}
            LEFT JOIN note n ON n.meeting_id = m.id AND n.team_id = :#{#req.idTeam}
            LEFT JOIN report r ON r.meeting_id = m.id AND r.team_id = :#{#req.idTeam}
            WHERE m.id = :#{#req.idMeeting}
                      """, nativeQuery = true)
    Optional<TeHomeWorkAndNoteMeetingResponse> searchDetailMeetingTeamByIdMeIdTeam(@Param("req") TeFindMeetingRequest req);

    @Query(value = """
            SELECT  
                m.id as id,
                m.name as name,
                m.class_id as class_id,
                m.meeting_date as meeting_date,
                mp.name as meeting_period, mp.start_hour as start_hour, mp.start_minute as start_minute ,
                mp.end_hour as end_hour, mp.end_minute as end_minute,             
                m.status_meeting as status_meeting
            FROM meeting m
            JOIN meeting_period mp ON mp.id = m.meeting_period
            JOIN class c ON c.id = m.class_id
            WHERE m.class_id = :#{#idClass}
            ORDER BY m.meeting_date ASC
                     """, nativeQuery = true)
    List<TeMeetingCustomToAttendanceResponse> findMeetingCustomToAttendanceByIdClass(@Param("idClass") String idClass);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY m.meeting_date ASC) AS stt,
                 c.id as id_class,
                 c.code as code_class,
                 m.id as id_meeting,
                 m.meeting_date as meeting_date,
                 mp.name as meeting_period, mp.start_hour as start_hour, mp.start_minute as start_minute ,
                 mp.end_hour as end_hour, mp.end_minute as end_minute,
                 m.name as name_meeting,
                 m.type_meeting as type_meeting,
                 m.address as address_meeting,
                 m.descriptions as descriptions_meeting,
                 l.name as level,
                 m.notes as notes
             FROM class c
             JOIN meeting m ON m.class_id = c.id
             JOIN meeting_period mp ON mp.id = m.meeting_period
             JOIN activity ac ON ac.id = c.activity_id
             JOIN level l on l.id = ac.level_id
             WHERE m.teacher_id = :#{#req.idTeacher} AND DATE(FROM_UNIXTIME(m.meeting_date / 1000)) = CURDATE()
             and m.status_meeting = 0
             ORDER BY m.meeting_date ASC
            """, countQuery = """
            SELECT DISTINCT(m.id)
                         FROM class c
                         JOIN meeting m ON m.class_id = c.id
                         JOIN meeting_period mp ON mp.id = m.meeting_period
                         JOIN activity ac ON ac.id = c.activity_id
                         JOIN level l on l.id = ac.level_id
                         WHERE m.teacher_id = :#{#req.idTeacher} AND DATE(FROM_UNIXTIME(m.meeting_date / 1000)) = CURDATE()
            """
            , nativeQuery = true)
    List<TeScheduleMeetingClassResponse> searchScheduleToDayByIdTeacherAndMeetingDate(@Param("req") TeFindScheduleMeetingClassRequest req);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY m.meeting_date ASC) AS stt,
                 c.id as id_class,
                 c.code as code_class,
                 m.id as id_meeting,
                 m.meeting_date as meeting_date,
                 mp.name as meeting_period, mp.start_hour as start_hour, mp.start_minute as start_minute ,
                 mp.end_hour as end_hour, mp.end_minute as end_minute,
                 m.name as name_meeting,
                 m.type_meeting as type_meeting,
                 m.address as address_meeting,
                 m.descriptions as descriptions_meeting,
                 l.name as level,
                 m.notes as notes
             FROM class c
             JOIN meeting m ON m.class_id = c.id
             JOIN meeting_period mp ON mp.id = m.meeting_period
             JOIN activity ac ON ac.id = c.activity_id
             JOIN level l on l.id = ac.level_id
             WHERE m.teacher_id = :#{#req.idTeacher} AND
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
                         JOIN meeting_period mp ON mp.id = m.meeting_period
                         JOIN activity ac ON ac.id = c.activity_id
                         JOIN level l on l.id = ac.level_id
                         WHERE m.teacher_id = :#{#req.idTeacher} AND 
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
    Page<TeScheduleMeetingClassResponse> searchScheduleNowToTimeByIdTeacher(@Param("req") TeFindScheduleNowToTime req, Pageable pageable);

    Optional<Meeting> findMeetingById(String id);

    @Transactional
    @Modifying
    @Query(value = """
            UPDATE meeting AS m
            SET m.status_meeting = 1, m.notes = "Buổi nghỉ của lớp"
            WHERE DATE(FROM_UNIXTIME(m.meeting_date / 1000)) <= CURDATE() AND m.status_meeting = 0
            AND (
                (m.meeting_period = 0 AND TIME(FROM_UNIXTIME(m.meeting_date / 1000)) > '09:15:00')
                OR
                (m.meeting_period = 1 AND TIME(FROM_UNIXTIME(m.meeting_date / 1000)) > '11:25:00')
                OR 
                (m.meeting_period = 2 AND TIME(FROM_UNIXTIME(m.meeting_date / 1000)) > '14:00:00')
                OR
                (m.meeting_period = 3 AND TIME(FROM_UNIXTIME(m.meeting_date / 1000)) > '16:10:00')
                OR
                (m.meeting_period = 4 AND TIME(FROM_UNIXTIME(m.meeting_date / 1000)) > '18:20:00')
                OR
                (m.meeting_period = 5 AND TIME(FROM_UNIXTIME(m.meeting_date / 1000))> '20:30:00')
                 OR
                (m.meeting_period = 6 AND TIME(FROM_UNIXTIME(m.meeting_date / 1000))> '22:40:00')
            )
            AND NOT EXISTS (
                SELECT 1
                FROM attendance a
                WHERE a.meeting_id = m.id
            )
            """, nativeQuery = true)
    int updateStatusMeetingRest();

}