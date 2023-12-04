package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindScheduleMeetingClassRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindScheduleNowToTime;
import com.labreportapp.labreport.core.teacher.model.response.TeDetailTeamReportRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeHomeWorkAndNoteMeetingResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeHwNoteReportListRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingCustomToAttendanceResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeScheduleMeetingClassResponse;
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
                m.id AS id,
                m.name AS name,
                m.descriptions AS descriptions,
                m.meeting_date AS meeting_date,
                m.type_meeting AS type_meeting,
                  m.meeting_period AS id_meeting_period,
                mp.name AS meeting_period, mp.start_hour AS start_hour, mp.start_minute AS start_minute ,
                mp.end_hour AS end_hour, mp.end_minute AS end_minute,
                m.class_id AS class_id,
                m.teacher_id AS teacher_id,
                m.status_meeting AS status_meeting,
                 c.status_class AS status_class
            FROM meeting m
            JOIN meeting_period mp ON mp.id = m.meeting_period
            JOIN class c ON c.id = m.class_id
            WHERE m.class_id = :#{#req.idClass}
            ORDER BY m.meeting_date DESC, mp.name DESC
                     """, nativeQuery = true)
    List<TeMeetingResponse> findMeetingByIdClass(@Param("req") TeFindMeetingRequest req);

    @Query(value = """
            SELECT  m.id AS id,
                m.name AS name,
                m.descriptions AS descriptions,
                m.meeting_date AS meeting_date,
                m.meeting_period AS id_meeting_period,
                mp.name AS meeting_period, mp.start_hour AS start_hour, mp.start_minute AS start_minute ,
                mp.end_hour AS end_hour, mp.end_minute AS end_minute,
                m.type_meeting AS type_meeting,
                mp.name AS meeting_period,
                m.notes AS notes,
                m.class_id AS class_id,
                m.teacher_id AS teacher_id,
                 m.status_meeting AS status_meeting,
                 c.status_class AS status_class
            FROM meeting m
            JOIN class c ON c.id = m.class_id
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
                r.id AS idReport,
                r.descriptions AS descriptionsReport,
                m.meeting_date AS meeting_date,
                m.meeting_period AS id_meeting_period,
                mp.name AS meeting_period, mp.start_hour AS start_hour, mp.start_minute AS start_minute,
                mp.end_hour AS end_hour, mp.end_minute AS end_minute
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
                m.id AS id,
                m.name AS name,
                m.class_id AS class_id,
                m.meeting_date AS meeting_date,
                mp.name AS meeting_period, mp.start_hour AS start_hour, mp.start_minute AS start_minute,
                mp.end_hour AS end_hour, mp.end_minute AS end_minute,             
                m.status_meeting AS status_meeting
            FROM meeting m
            JOIN meeting_period mp ON mp.id = m.meeting_period
            JOIN class c ON c.id = m.class_id
            WHERE m.class_id = :#{#idClass}
            ORDER BY m.meeting_date ASC
                     """, nativeQuery = true)
    List<TeMeetingCustomToAttendanceResponse> findMeetingCustomToAttendanceByIdClass(@Param("idClass") String idClass);

//    @Query(value = """
//            SELECT ROW_NUMBER() OVER(ORDER BY m.meeting_date DESC) AS stt,
//                 c.id AS id_class,
//                 c.code AS code_class,
//                 m.id AS id_meeting,
//                 m.meeting_date AS meeting_date,
//                 m.meeting_period AS id_meeting_period,
//                 mp.name AS meeting_period, mp.start_hour AS start_hour, mp.start_minute AS start_minute ,
//                 mp.end_hour AS end_hour, mp.end_minute AS end_minute,
//                 m.name AS name_meeting,
//                 m.type_meeting AS type_meeting,
//                 m.address AS address_meeting,
//                 m.descriptions AS descriptions_meeting,
//                 l.name AS level,
//                 m.notes AS notes,
//                 m.status_meeting AS status_meeting
//             FROM class c
//             JOIN meeting m ON m.class_id = c.id
//             LEFT JOIN meeting_period mp ON mp.id = m.meeting_period
//             JOIN activity ac ON ac.id = c.activity_id
//             JOIN level l ON l.id = ac.level_id
//             WHERE m.teacher_id = :#{#req.idTeacher} AND
//              DATE(FROM_UNIXTIME(m.meeting_date / 1000)) = CURDATE()
//                AND m.status_meeting = 0
//             ORDER BY m.meeting_date DESC
//            """, countQuery = """
//            SELECT DISTINCT(m.id)
//                         FROM class c
//                         JOIN meeting m ON m.class_id = c.id
//                       LEFT  JOIN meeting_period mp ON mp.id = m.meeting_period
//                         JOIN activity ac ON ac.id = c.activity_id
//                         JOIN level l ON l.id = ac.level_id
//                         WHERE m.teacher_id = :#{#req.idTeacher} AND DATE(FROM_UNIXTIME(m.meeting_date / 1000)) = CURDATE()
//                       AND m.status_meeting = 0
//                        ORDER BY m.meeting_date DESC
//            """
//            , nativeQuery = true)
//    List<TeScheduleMeetingClassResponse> searchScheduleToDayByIdTeacherAndMeetingDate(@Param("req") TeFindScheduleMeetingClassRequest req);


    @Query(value = """
    SELECT  ROW_NUMBER() OVER(ORDER BY m.meeting_date DESC) AS stt,
        c.id AS id_class,
        c.code AS code_class,
        m.id AS id_meeting,
        m.meeting_date AS meeting_date,
        m.meeting_period AS id_meeting_period,
        mp.name AS meeting_period, 
        mp.start_hour AS start_hour, 
        mp.start_minute AS start_minute,
        mp.end_hour AS end_hour, 
        mp.end_minute AS end_minute,
        m.name AS name_meeting,
        m.type_meeting AS type_meeting,
        m.address AS address_meeting,
        m.descriptions AS descriptions_meeting,
        l.name AS level,
        m.notes AS notes,
        m.status_meeting AS status_meeting
    FROM 
        class c
    JOIN 
        meeting m ON m.class_id = c.id
    LEFT JOIN 
        meeting_period mp ON mp.id = m.meeting_period
    JOIN 
        activity ac ON ac.id = c.activity_id
    JOIN 
        level l ON l.id = ac.level_id
    WHERE 
        m.teacher_id = :#{#req.idTeacher} AND
        m.meeting_date BETWEEN UNIX_TIMESTAMP(CURRENT_DATE()) * 1000 AND UNIX_TIMESTAMP(DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY)) * 1000 AND
        m.status_meeting = 0
    ORDER BY 
        m.meeting_date DESC
    """,countQuery = """
    SELECT COUNT(m.id) FROM 
        class c
    JOIN 
        meeting m ON m.class_id = c.id
    LEFT JOIN 
        meeting_period mp ON mp.id = m.meeting_period
    JOIN 
        activity ac ON ac.id = c.activity_id
    JOIN 
        level l ON l.id = ac.level_id
    WHERE 
        m.teacher_id = :#{#req.idTeacher} AND 
        m.meeting_date BETWEEN UNIX_TIMESTAMP(CURRENT_DATE()) * 1000 AND UNIX_TIMESTAMP(DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY)) * 1000 AND
        m.status_meeting = 0
    ORDER BY 
        m.meeting_date DESC
    """,
            nativeQuery = true)
    List<TeScheduleMeetingClassResponse> searchScheduleToDayByIdTeacherAndMeetingDate(@Param("req") TeFindScheduleMeetingClassRequest req);


    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY m.meeting_date ASC) AS stt,
                 c.id AS id_class,
                 c.code AS code_class,
                 m.id AS id_meeting,
                 m.meeting_date AS meeting_date,
                 m.meeting_period AS id_meeting_period,
                 mp.name AS meeting_period, mp.start_hour AS start_hour, mp.start_minute AS start_minute ,
                 mp.end_hour AS end_hour, mp.end_minute AS end_minute,
                 m.name AS name_meeting,
                 m.type_meeting AS type_meeting,
                 m.address AS address_meeting,
                 m.descriptions AS descriptions_meeting,
                 l.name AS level,
                 m.notes AS notes,
                 m.status_meeting AS status_meeting
             FROM class c
             JOIN meeting m ON m.class_id = c.id
             LEFT JOIN meeting_period mp ON mp.id = m.meeting_period
             JOIN activity ac ON ac.id = c.activity_id
             JOIN level l ON l.id = ac.level_id
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
             ORDER BY m.meeting_date ASC,mp.name ASC
            """, countQuery = """
              SELECT COUNT(m.id)
                         FROM class c
                         JOIN meeting m ON m.class_id = c.id
                         LEFT JOIN meeting_period mp ON mp.id = m.meeting_period
                         JOIN activity ac ON ac.id = c.activity_id
                         JOIN level l ON l.id = ac.level_id
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

    @Query(value = """
            WITH get_meeting_not_attend AS (
                SELECT m.id AS id_meeting
                FROM meeting m
                LEFT JOIN attendance a ON m.id = a.meeting_id
                WHERE m.status_meeting = 0  AND m.meeting_date <= :currentDate
                AND a.meeting_id IS NULL
            )
            SELECT *  FROM meeting m
            WHERE m.status_meeting = 0 AND  m.meeting_date <= :currentDate
                AND m.id IN (SELECT id_meeting FROM get_meeting_not_attend)
            ORDER BY m.name ASC;
            """, nativeQuery = true)
    List<Meeting> findMeetingToDayUpdate(@Param("currentDate") Long currentDate);

    @Query(value = """
            SELECT t.id AS idTeam,
                t.name AS nameTeam,
                m.id AS idMeeting,
                m.name AS nameMeeting,
                m.descriptions AS descriptionsMeeting,
                h.id AS idHomeWork,
                h.descriptions AS descriptionsHomeWork,
                n.id AS idNote,
                n.descriptions AS descriptionsNote,
                r.id AS idReport,
                r.descriptions AS descriptionsReport,
                m.meeting_date AS meeting_date,
                m.meeting_period AS id_meeting_period,
                mp.name AS meeting_period, mp.start_hour AS start_hour, mp.start_minute AS start_minute,
                mp.end_hour AS end_hour, mp.end_minute AS end_minute
            FROM meeting m
            JOIN meeting_period mp ON mp.id = m.meeting_period
            JOIN class c ON c.id = m.class_id
            JOIN team t ON t.class_id = c.id
            LEFT JOIN home_work h ON h.meeting_id = m.id AND h.team_id = t.id
            LEFT JOIN note n ON n.meeting_id = m.id AND n.team_id = t.id
            LEFT JOIN report r ON r.meeting_id = m.id AND r.team_id = t.id
            WHERE m.class_id = :#{#idClass}
                      """, nativeQuery = true)
    List<TeHwNoteReportListRespone> searchHwNoteReport(@Param("idClass") String idClass);
}