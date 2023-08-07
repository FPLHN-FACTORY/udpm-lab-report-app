package com.labreportapp.core.teacher.repository;

import com.labreportapp.core.teacher.model.response.TeSemesterRespone;
import com.labreportapp.entity.Semester;
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
            SELECT s.id as id, s.name as name FROM semester s
            ORDER BY s.end_time DESC
            """, nativeQuery = true)
    List<TeSemesterRespone> getAllSemesters();

}
