package com.labreportapp.core.admin.repository;

import com.labreportapp.core.admin.model.request.AdFindActivityRequest;
import com.labreportapp.core.admin.model.response.AdActivityResponse;
import com.labreportapp.entity.Activity;
import com.labreportapp.entity.Semester;
import com.labreportapp.repository.ActivityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdActivityRepository extends ActivityRepository {

    @Query(value = """
             SELECT ROW_NUMBER() OVER(ORDER BY act.last_modified_date DESC ) AS STT ,
                  act.id,
                  act.name,
                  act.start_time as startTime,
                  act.end_time as endTime,
                  act.level,
                  s.id as semesterId,
                  s.name as nameSemester
             FROM activity act JOIN semester s ON s.id = act.semester_id 
             WHERE  
             ( :#{#rep.name} IS NULL 
                OR :#{#rep.name} LIKE '' 
                OR act.name LIKE %:#{#rep.name}% )     
            """, countQuery = """
            SELECT COUNT(act.id) FROM activity act 
             WHERE  
             ( :#{#rep.name} IS NULL 
                OR :#{#rep.name} LIKE '' 
                OR act.name LIKE %:#{#rep.name}% )
            ORDER BY act.last_modified_date DESC
            """, nativeQuery = true)
    Page<AdActivityResponse> findByNameActivity(@Param("rep") AdFindActivityRequest rep, Pageable pageable);

    @Query(value = """
             SELECT ROW_NUMBER() OVER(ORDER BY act.last_modified_date DESC ) AS STT ,
                  act.id,
                  act.name,
                  act.start_time as startTime,
                  act.end_time as endTime,
                  act.level,
                  s.id as semesterId,
                  s.name as nameSemester
             FROM activity act JOIN semester s ON s.id = act.semester_id 
             WHERE  
             ( :#{#rep.level} IS NULL 
                OR :#{#rep.level} LIKE '' 
                OR act.name LIKE %:#{#rep.level}% )     
            """, countQuery = """
            SELECT COUNT(act.id) FROM activity act 
             WHERE  
             ( :#{#rep.level} IS NULL 
                OR :#{#rep.level} LIKE '' 
                OR act.name LIKE %:#{#rep.level}% )
            ORDER BY act.last_modified_date DESC
            """, nativeQuery = true)
    Page<AdActivityResponse> findByLevelActivity(@Param("rep") AdFindActivityRequest rep, Pageable pageable);

    @Query(value = """
             SELECT ROW_NUMBER() OVER(ORDER BY act.last_modified_date DESC ) AS STT ,
                  act.id,
                  act.name,
                  act.start_time as startTime,
                  act.end_time as endTime,
                  act.level,
                  s.id as semesterId,
                  s.name as nameSemester
             FROM activity act JOIN semester s ON s.id = act.semester_id 
             WHERE  
             ( :#{#rep.semesterId} IS NULL 
                OR :#{#rep.semesterId} LIKE '' 
                OR act.name LIKE %:#{#rep.semesterId}% )     
            """, countQuery = """
            SELECT COUNT(act.id) FROM activity act 
             WHERE  
             ( :#{#rep.semesterId} IS NULL 
                OR :#{#rep.semesterId} LIKE '' 
                OR act.name LIKE %:#{#rep.semesterId}% )
            ORDER BY act.last_modified_date DESC
            """, nativeQuery = true)
    Page<AdActivityResponse> findBySemesterActivity(@Param("rep") AdFindActivityRequest rep, Pageable pageable);

    @Query(value = """
            SELECT act.name FROM activity act  WHERE act.name = :ma
            """,nativeQuery = true)
    String getMaActivity(@Param("ma") String ma);

    @Query(value = """
            SELECT act.id FROM activity act  WHERE :status IS NOT NULL
            """,nativeQuery = true)
    List<String> getAllIdByStatus (@Param("status") String status);

//    @Query("SELECT act.id FROM Activity act WHERE act.name = :name AND act.id <> :id")
//    String findByCodeLabel (@Param("code") String codeLabel ,
//                            @Param("id") String id);
}
