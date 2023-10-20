package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.labreport.core.teacher.model.response.TeHomeWorkAndNoteMeetingResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeTeamsRespone;
import com.labreportapp.labreport.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hieundph25894
 */
@Repository
public interface TeTeamsRepositoty extends JpaRepository<Team, String> {

    @Query(value = """
              WITH MinReport AS (
                  SELECT MIN(r.id) as id, r.team_id
                  FROM report r
                  WHERE r.meeting_id = :#{#req.idMeeting}
                  GROUP BY team_id
              )
              SELECT
                  t.id as id,
                  t.name as name,
                  t.subject_name as subjectName,
                  t.created_date as createdDate,
                  t.project_id as project_id,
                  mr.id as report_id,
                  r.descriptions as descriptions_report
              FROM team t
              LEFT JOIN MinReport mr ON mr.team_id = t.id
              LEFT JOIN report r ON r.id = mr.id
              WHERE t.class_id = :#{#req.idClass}
              ORDER BY
            CASE
              WHEN SUBSTRING(t.name, 6) REGEXP '^[0-9]+$' THEN CAST(SUBSTRING(t.name, 6) AS SIGNED)
              ELSE NULL
            END ASC,
            CASE
              WHEN SUBSTRING(t.name, 6) REGEXP '^[0-9]+$' THEN ROW_NUMBER() OVER (ORDER BY t.name)
              ELSE 0
            END ASC,
            t.created_date ASC;
               """, nativeQuery = true)
    List<TeTeamsRespone> findTeamsByIdClass(@Param("req") TeFindStudentClasses req);

    @Query(value = """
                SELECT t.name FROM team t WHERE t.name = :code
            """, nativeQuery = true)
    String getTeamByCode(@Param("code") String code);

    @Query(value = """
            SELECT  DISTINCT 
                t.id as idTeam,
                t.name as nameTeam,
                m.id as idMeeting,
                m.name as nameMeeting,
                m.descriptions as descriptionsMeeting,
                t.name  as nameTeam,
                t.subject_name as subjectName,
                m.created_date as createdDate,
                h.id as idHomeWork,
                h.descriptions as descriptionsHomeWork,
                n.id as idNote,
                n.descriptions as descriptionsNote,
                m.class_id as class_id
            FROM team t 
            JOIN meeting m ON m.class_id = t.class_id
            LEFT JOIN home_work h ON h.team_id = t.id
            LEFT JOIN note n ON n.team_id = t.id
            WHERE m.class_id = :#{#req.idClass} and m.id = :#{#req.idMeeting}
                     """, nativeQuery = true)
    List<TeHomeWorkAndNoteMeetingResponse> findTeamAndHomeWorkAndNoteByIdClassAndIdMeeting(@Param("req") TeFindMeetingRequest req);

    @Query(value = """
             SELECT  * FROM team t WHERE t.class_id = :#{#idClass} 
             ORDER BY t.name ASC, t.created_date ASC
            """, nativeQuery = true)
    List<Team> getTeamByClassId(@Param("idClass") String idClass);

    @Query(value = """
             SELECT IFNULL(
                    CONCAT(' ', MAX(CAST(SUBSTRING_INDEX(t.name, ' ', -1) AS SIGNED)) + 1),
                    '1'
                ) AS new_name
            FROM team t
            LEFT JOIN class c ON t.class_id = c.id
             WHERE c.id = :idClass
             """, nativeQuery = true)
    Integer getNameNhomAuto(@Param("idClass") String idClass);

}
