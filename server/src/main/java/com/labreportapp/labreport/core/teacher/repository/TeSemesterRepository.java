package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.core.teacher.model.response.TeSemesterResponse;
import com.labreportapp.labreport.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hieundph25894
 */
@Repository
public interface TeSemesterRepository extends JpaRepository<Semester, String> {

    @Query(value = """
            SELECT s.id as id, s.name as name, s.start_time as start_time, s.end_time as end_time FROM semester s
            ORDER BY s.end_time DESC
            """, nativeQuery = true)
    List<TeSemesterResponse> getAllSemesters();

}
