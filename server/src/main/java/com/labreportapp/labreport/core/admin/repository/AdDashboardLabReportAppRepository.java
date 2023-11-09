package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.request.AdDashboardLabReportAppRequest;
import com.labreportapp.labreport.repository.ClassRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author todo thangncph26123
 */
public interface AdDashboardLabReportAppRepository extends ClassRepository {

    @Query(value = """
            SELECT COUNT(a.id) FROM class a 
            JOIN activity b ON a.activity_id = b.id
            JOIN semester c ON b.semester_id = c.id
            WHERE b.id = :#{#req.idActivity} AND c.id = :#{#req.idSemester}
            """, nativeQuery = true)
    Integer getTongSoLopHoc(@Param("req") AdDashboardLabReportAppRequest req);

    @Query(value = """
            SELECT COUNT(DISTINCT a.teacher_id) FROM class a 
            JOIN activity b ON a.activity_id = b.id
            JOIN semester c ON b.semester_id = c.id
            WHERE b.id = :#{#req.idActivity} AND c.id = :#{#req.idSemester}
            """, nativeQuery = true)
    Integer getTongSoGiangVien(@Param("req") AdDashboardLabReportAppRequest req);

    @Query(value = """
            SELECT COUNT(DISTINCT d.student_id) FROM class a 
            JOIN student_classes d ON d.class_id = a.id
            JOIN activity b ON a.activity_id = b.id
            JOIN semester c ON b.semester_id = c.id
            WHERE b.id = :#{#req.idActivity} AND c.id = :#{#req.idSemester}
            """, nativeQuery = true)
    Integer getTongSoSinhVien(@Param("req") AdDashboardLabReportAppRequest req);

    @Query(value = """
            SELECT COUNT(a.id) FROM class a 
            JOIN activity b ON a.activity_id = b.id
            JOIN semester c ON b.semester_id = c.id
            WHERE b.id = :#{#req.idActivity} AND c.id = :#{#req.idSemester}
            AND a.teacher_id IS NULL
            """, nativeQuery = true)
    Integer getTongSoLopChuaCoGiangVien(@Param("req") AdDashboardLabReportAppRequest req);

    @Query(value = """
            SELECT COUNT(a.id) FROM class a 
            JOIN activity b ON a.activity_id = b.id
            JOIN semester c ON b.semester_id = c.id
            WHERE b.id = :#{#req.idActivity} AND c.id = :#{#req.idSemester}
            AND a.class_size >= :minClassSize
            """, nativeQuery = true)
    Integer getTongSoLopDuDieuKien(@Param("req") AdDashboardLabReportAppRequest req, @Param("minClassSize") Integer minClassSize);

    @Query(value = """
            SELECT COUNT(a.id) FROM class a 
            JOIN activity b ON a.activity_id = b.id
            JOIN semester c ON b.semester_id = c.id
            WHERE b.id = :#{#req.idActivity} AND c.id = :#{#req.idSemester}
            AND a.status_teacher_edit = 0
            """, nativeQuery = true)
    Integer getTongLopGiangVienChinhSua(@Param("req") AdDashboardLabReportAppRequest req);

    @Query(value = """
            SELECT COUNT(a.id) FROM level a
            """, nativeQuery = true)
    Integer getTongSoLevel();
}
