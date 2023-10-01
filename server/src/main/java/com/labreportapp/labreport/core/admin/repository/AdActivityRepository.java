package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.request.AdFindActivityRequest;
import com.labreportapp.labreport.core.admin.model.response.AdActivityResponse;
import com.labreportapp.labreport.core.admin.model.response.AdActivityLevelResponse;
import com.labreportapp.labreport.repository.ActivityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdActivityRepository extends ActivityRepository {

    @Query(value = """
             SELECT ROW_NUMBER() OVER(ORDER BY act.created_date DESC ) AS STT ,
                  act.id,
                  act.code,
                  act.name,
                  act.start_time as startTime,
                  act.end_time as endTime,
                  lv.name as level,
                  s.id as semesterId,
                  s.name as nameSemester,
                  act.descriptions, act.allow_use_trello
             FROM activity act 
             JOIN level lv on lv.id = act.level_id
             JOIN semester s ON s.id = act.semester_id 
             WHERE  
             ( :#{#rep.name} IS NULL 
                OR :#{#rep.name} LIKE '' 
                OR act.name LIKE %:#{#rep.name}% )   
                AND ( :#{#rep.level} IS NULL  OR :#{#rep.level} LIKE ''  OR lv.id = :#{#rep.level} ) 
                AND   ( :#{#rep.semesterId} IS NULL  OR :#{#rep.semesterId} LIKE '' OR s.id = :#{#rep.semesterId} )   
                ORDER BY act.created_date DESC
            """, countQuery = """
            SELECT COUNT(act.id) FROM activity act JOIN semester s ON s.id = act.semester_id 
            WHERE  
             ( :#{#rep.name} IS NULL 
                OR :#{#rep.name} LIKE '' 
                OR act.name LIKE %:#{#rep.name}% )   
                AND ( :#{#rep.level} IS NULL 
                OR :#{#rep.level} LIKE '' 
                OR lv.id = CAST(:#{#rep.level} AS SIGNED) ) AND ( :#{#rep.semesterId} IS NULL 
                OR :#{#rep.semesterId} LIKE '' 
                OR s.id = :#{#rep.semesterId} )   
                ORDER BY act.created_date
            """, nativeQuery = true)
    Page<AdActivityResponse> findByNameActivity(@Param("rep") AdFindActivityRequest rep, Pageable pageable);

    @Query(value = """
             SELECT ROW_NUMBER() OVER(ORDER BY act.last_modified_date DESC ) AS STT ,
                  act.id,
                  act.code,
                  act.name,
                  act.start_time as startTime,
                  act.end_time as endTime,
                  lv.name as level,
                  s.id as semesterId,
                  s.name as nameSemester,
                  act.descriptions
             FROM activity act 
             JOIN level as lv on lv.id act.level_id
             JOIN semester s ON s.id = act.semester_id 
             WHERE  
             ( :#{#rep.level} IS NULL 
                OR :#{#rep.level} LIKE '' 
                OR lv.id LIKE %:#{#rep.level}% )     
            """, countQuery = """
            SELECT COUNT(act.id) FROM activity act 
             WHERE  
             ( :#{#rep.level} IS NULL 
                OR :#{#rep.level} LIKE '' 
                OR lv.id LIKE %:#{#rep.level}% )
            ORDER BY act.last_modified_date DESC
            """, nativeQuery = true)
    Page<AdActivityResponse> findByLevelActivity(@Param("rep") AdFindActivityRequest rep, Pageable pageable);

    @Query(value = """
             SELECT ROW_NUMBER() OVER(ORDER BY act.last_modified_date DESC ) AS STT ,
                  act.id,
                  act.code,
                  act.name,
                  act.start_time as startTime,
                  act.end_time as endTime,
                  act.level,
                  s.id as semesterId,
                  s.name as nameSemester,
                  act.descriptions
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
            SELECT act.name FROM activity act WHERE act.name = :ma AND act.semester_id = :idSemester
            """, nativeQuery = true)
    String getMaActivity(@Param("ma") String ma, @Param("idSemester") String idSemester);

    @Query(value = """
            SELECT act.name FROM activity act WHERE act.name = :ma AND act.id <> :id AND act.semester_id = :idSemester
            """, nativeQuery = true)
    String getMaActivityUpdate(@Param("ma") String ma, @Param("id") String id, @Param("idSemester") String idSemester);

    @Query(value = """
            SELECT act.id FROM activity act  WHERE :status IS NOT NULL
            """, nativeQuery = true)
    List<String> getAllIdByStatus(@Param("status") String status);

    @Query(value = """
            SELECT COUNT(a.id) FROM class a WHERE a.activity_id = :idActivity
            """, nativeQuery = true)
    Integer countClassInActivity(@Param("idActivity") String idActivity);

    @Query(value = """
            select lv.id, lv.name from level lv order by lv.created_date desc 
            """, nativeQuery = true)
    List<AdActivityLevelResponse> getAllLevel();

    @Query(value = """
            SELECT a.id FROM activity a WHERE a.code = :code AND a.semester_id = :idSemester
            """, nativeQuery = true)
    String findActivtyByCode(@Param("code") String code, @Param("idSemester") String idSemester);

    @Query(value = """
            SELECT a.id FROM activity a WHERE a.code = :code AND a.id <> :id AND a.semester_id = :idSemester
            """, nativeQuery = true)
    String findActivtyByCodeUpdate(@Param("code") String code, @Param("id") String id, @Param("idSemester") String idSemester);

//    @Query("SELECT act.id FROM Activity act WHERE act.name = :name AND act.id <> :id")
//    String findByCodeLabel (@Param("code") String codeLabel ,
//                            @Param("id") String id);
}
