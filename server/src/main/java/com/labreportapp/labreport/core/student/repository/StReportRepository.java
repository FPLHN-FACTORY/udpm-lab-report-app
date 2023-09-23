package com.labreportapp.labreport.core.student.repository;

import com.labreportapp.labreport.core.student.model.request.StFindMeetingRequest;
import com.labreportapp.labreport.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author quynhncph26201
 */
@Repository
public interface StReportRepository extends JpaRepository<Report, String> {
//    @Query(value = """
//            SELECT m.id AS idMeeting,
//            m.name AS nameMeeting,
//            m.descriptions AS descriptionsMeeting,
//            m.created_date AS createdDate,
//            h.id AS idReport,
//            h.descriptions AS descriptionsReport,
//             FROM meeting m
//             JOIN report h ON h.meeting_id = m.id
//             WHERE m.id = :#{#req.idMeeting} and h.team_id = :#{#req.idTeam}
//                      """, nativeQuery = true)
//    Optional<StReportResponse> DetailReportInMeetingTeamByIdMeIdTeam(@Param("req") StFindMeetingRequest req);
}

