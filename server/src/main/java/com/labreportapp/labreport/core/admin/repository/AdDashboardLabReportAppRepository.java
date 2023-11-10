package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.request.AdDashboardLabReportAppRequest;
import com.labreportapp.labreport.core.admin.model.response.AdDashboardClassResponse;
import com.labreportapp.labreport.repository.ClassRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author todo thangncph26123
 */
public interface AdDashboardLabReportAppRepository extends ClassRepository {

    @Query(value = """
            SELECT COUNT(a.id) FROM class a 
            JOIN activity b ON a.activity_id = b.id
            JOIN semester c ON b.semester_id = c.id
            WHERE (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR b.id = :#{#req.idActivity})
            AND (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR c.id = :#{#req.idSemester})
            """, nativeQuery = true)
    Integer getTongSoLopHoc(@Param("req") AdDashboardLabReportAppRequest req);

    @Query(value = """
            SELECT COUNT(DISTINCT a.teacher_id) FROM class a 
            JOIN activity b ON a.activity_id = b.id
            JOIN semester c ON b.semester_id = c.id
            WHERE (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR b.id = :#{#req.idActivity})
            AND (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR c.id = :#{#req.idSemester})
            """, nativeQuery = true)
    Integer getTongSoGiangVien(@Param("req") AdDashboardLabReportAppRequest req);

    @Query(value = """
            SELECT COUNT(DISTINCT d.student_id) FROM class a 
            JOIN student_classes d ON d.class_id = a.id
            JOIN activity b ON a.activity_id = b.id
            JOIN semester c ON b.semester_id = c.id
            WHERE (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR b.id = :#{#req.idActivity})
            AND (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR c.id = :#{#req.idSemester})
            """, nativeQuery = true)
    Integer getTongSoSinhVien(@Param("req") AdDashboardLabReportAppRequest req);

    @Query(value = """
            SELECT COUNT(a.id) FROM class a 
            JOIN activity b ON a.activity_id = b.id
            JOIN semester c ON b.semester_id = c.id
            WHERE (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR b.id = :#{#req.idActivity})
            AND (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR c.id = :#{#req.idSemester})
            AND a.teacher_id IS NULL
            """, nativeQuery = true)
    Integer getTongSoLopChuaCoGiangVien(@Param("req") AdDashboardLabReportAppRequest req);

    @Query(value = """
            SELECT COUNT(a.id) FROM class a 
            JOIN activity b ON a.activity_id = b.id
            JOIN semester c ON b.semester_id = c.id
            WHERE (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR b.id = :#{#req.idActivity})
            AND (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR c.id = :#{#req.idSemester})
            AND a.class_size >= :minClassSize
            """, nativeQuery = true)
    Integer getTongSoLopDuDieuKien(@Param("req") AdDashboardLabReportAppRequest req, @Param("minClassSize") Integer minClassSize);

    @Query(value = """
            SELECT COUNT(a.id) FROM class a 
            JOIN activity b ON a.activity_id = b.id
            JOIN semester c ON b.semester_id = c.id
            WHERE (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR b.id = :#{#req.idActivity})
            AND (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR c.id = :#{#req.idSemester})
            AND a.status_teacher_edit = 0
            """, nativeQuery = true)
    Integer getTongLopGiangVienChinhSua(@Param("req") AdDashboardLabReportAppRequest req);

    @Query(value = """
            SELECT COUNT(a.id) FROM level a
            """, nativeQuery = true)
    Integer getTongSoLevel();

    @Query(value = """
            SELECT DISTINCT a.teacher_id FROM class a 
            JOIN activity b ON a.activity_id = b.id
            JOIN semester c ON b.semester_id = c.id
            WHERE (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR b.id = :#{#req.idActivity})
            AND (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR c.id = :#{#req.idSemester})
            """, nativeQuery = true)
    List<String> getListGiangVien(@Param("req") AdDashboardLabReportAppRequest req);

    @Query(value = """
            WITH NumberPost AS (
                SELECT a.id, COUNT(b.id) AS number_post
                FROM class a LEFT JOIN post b ON a.id = b.class_id
                WHERE a.teacher_id = :idTeacher
            ), NumberTeam AS (
                SELECT a.id, COUNT(b.id) AS number_team
                FROM class a LEFT JOIN team b ON a.id = b.class_id
                WHERE a.teacher_id = :idTeacher
            ), NumberMeeting AS (
                SELECT a.id, COUNT(b.id) AS number_meeting
                FROM class a LEFT JOIN meeting b ON a.id = b.class_id
                WHERE a.teacher_id = :idTeacher
            ), NumberMeetingTookPlace AS (
                SELECT a.id, COUNT(b.id) AS number_meeting_took_place
                FROM class a LEFT JOIN meeting b ON a.id = b.class_id
                WHERE a.teacher_id = :idTeacher AND b.meeting_date <= :currentTime
            ), NumberStudentPass AS (
                SELECT a.id, COUNT(b.id) AS number_student_pass
                FROM class a LEFT JOIN student_classes b ON a.id = b.class_id
                WHERE a.teacher_id = :idTeacher AND b.status = 0
            ), NumberStudentFail AS (
                SELECT a.id, COUNT(b.id) AS number_student_fail
                FROM class a LEFT JOIN student_classes b ON a.id = b.class_id
                WHERE a.teacher_id = :idTeacher AND b.status = 1
            )
            SELECT a.id, a.code, a.class_size, c.name AS name_level,
            b.name AS name_activity, 
            COALESCE(numberPost.number_post, 0) AS number_post,
            COALESCE(numberTeam.number_team,  0) AS number_team,
            COALESCE(numberMeeting.number_meeting, 0) AS number_meeting,
            COALESCE(numberMeetingTookPlace.number_meeting_took_place, 0) AS number_meeting_took_place,
            COALESCE(numberStudentPass.number_student_pass, 0) AS number_student_pass,
            COALESCE(numberStudentFail.number_student_fail, 0) AS number_student_fail
            FROM class a
            JOIN activity b ON b.id = a.activity_id
            JOIN level c ON b.level_id = c.id 
            JOIN semester d ON d.id = b.semester_id
            LEFT JOIN NumberPost numberPost ON numberPost.id = a.id
            LEFT JOIN NumberTeam numberTeam ON numberTeam.id = a.id
            LEFT JOIN NumberMeeting numberMeeting ON numberMeeting.id = a.id
            LEFT JOIN NumberMeetingTookPlace numberMeetingTookPlace ON numberMeetingTookPlace.id = a.id
            LEFT JOIN NumberStudentPass numberStudentPass ON numberStudentPass.id = a.id
            LEFT JOIN NumberStudentFail numberStudentFail ON numberStudentFail.id = a.id
            WHERE a.teacher_id = :idTeacher
            AND (:idActivity IS NULL OR :idActivity LIKE '' OR b.id = :idActivity) 
            AND (:idSemester IS NULL OR :idSemester LIKE '' OR d.id = :idSemester)
            """, nativeQuery = true)
    List<AdDashboardClassResponse> getListClass(@Param("idTeacher") String idTeacher,
                                                @Param("idSemester") String idSemester,
                                                @Param("idActivity") String idActivity,
                                                @Param("currentTime") Long currentTime);
}
