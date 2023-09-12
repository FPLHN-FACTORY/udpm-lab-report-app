package com.labreportapp.core.student.repository;

import com.labreportapp.core.student.model.response.StPointDetailRespone;
import com.labreportapp.repository.PointRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StPointDetailRepository extends PointRepository {
    @Query(value = """
            SELECT a.check_point_phase1, a.check_point_phase2, a.final_point FROM point a WHERE a.class_id = :idClass AND a.student_id = :idStudent
            """, nativeQuery = true)
    StPointDetailRespone getPointMyClass(@Param("idClass") String idClass, @Param("idStudent") String idStudent);
}
