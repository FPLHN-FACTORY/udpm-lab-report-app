//package com.labreportapp.portalprojects.repository;
//
//import com.labreportapp.portalprojects.core.member.model.request.FindHistoryProgressBarRequest;
//import com.labreportapp.portalprojects.core.member.model.response.MeHistoryProgressResponse;
//import com.labreportapp.portalprojects.entity.HistoryProgress;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
///**
// * @author thangncph26123
// */
//@Repository(HistoryProgressRepository.NAME)
//public interface HistoryProgressRepository extends JpaRepository<HistoryProgress, String> {
//
//    public static final String NAME = "BaseHistoryProgressRepository";
//
//    @Query(value = """
//            SELECT progress_change, progress_date FROM history_progress
//            WHERE progress_date BETWEEN :#{#req.startTime} AND :#{#req.endTime}
//            AND project_id = :#{#req.projectId}
//            ORDER BY created_date ASC
//            """, nativeQuery = true)
//    List<MeHistoryProgressResponse> getHistoryProgressByStartTimeAndEndTime(@Param("req")FindHistoryProgressBarRequest req);
//}
