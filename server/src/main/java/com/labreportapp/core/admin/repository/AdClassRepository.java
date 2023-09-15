package com.labreportapp.core.admin.repository;

import com.labreportapp.core.admin.model.request.AdFindClassRequest;
import com.labreportapp.core.admin.model.response.*;
import com.labreportapp.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.repository.ClassRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author quynhncph26201
 */
@Repository
public interface AdClassRepository extends ClassRepository {

    @Query(value = """ 
            select a.code,a.start_time
            ,a.class_period,a.class_size,a.teacher_id,b.name as nameActivity 
              from class a join activity b on a.activity_id=b.id""", nativeQuery = true)
    List<AdClassResponse> getAllClass();

    @Query(value = """ 
              select a.code,a.start_time
              ,a.class_period,a.class_size,a.teacher_id,b.name as nameActivity ,b.semester_id as semesterId
                from class a join activity b join semester s 
                on s.id = b.semester_id 
                on a.activity_id=b.id
                WHERE
            (:#{#req.idSemester} IS NULL 
             OR :#{#req.idSemester} = '' 
             OR b.semester_id = :#{#req.idSemester})
                """, nativeQuery = true)
    List<AdClassResponse> getAllClassBySemester(@Param("req") AdFindClassRequest req);

    @Query(value = """ 
            sSELECT a.code, a.start_time, a.class_period
            , a.class_size, a.teacher_id, b.name AS nameActivity
                FROM class a
                JOIN activity b ON a.activity_id = b.id
                WHERE (a.code = :codeClass OR a.class_period = :classPeriod OR a.teacher_id = :idPerson)
                OR ((a.code = :codeClass AND a.class_period = :classPeriod)
                OR (a.code = :codeClass AND a.teacher_id = :idPerson)
                OR (a.class_period = :classPeriod AND a.teacher_id = :idPerson))
                OR (a.code = :codeClass AND a.class_period = :classPeriod AND a.teacher_id =:idPerson)"""
            , nativeQuery = true)
    List<AdClassResponse> findClassByCondition(@Param("codeClass") String code,
                                               @Param("classPeriod") Long classPeriod,
                                               @Param("idPerson") String idTeacher);

    @Query(value = """
            SELECT s.id as id, s.name as name FROM semester s
            ORDER BY s.end_time DESC
            """, nativeQuery = true)
    List<AdSemesterAcResponse> getAllSemesters();

    @Query(value = """
              SELECT a.id as id, a.name as name FROM activity a 
              WHERE
            (:#{#req.idSemester} IS NULL 
             OR :#{#req.idSemester} = '' 
             OR a.semester_id = :#{#req.idSemester})
              """, nativeQuery = true)
    List<AdActivityClassResponse> getAllByIdSemester(@Param("req") AdFindClassRequest req);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY c.last_modified_date DESC ) AS stt,
            c.id,
            c.code,c.start_time
            ,c.class_period,c.class_size,c.teacher_id,a.name as nameActivity 
            FROM activity a
            JOIN class c ON c.activity_id = a.id
            JOIN semester s ON s.id = a.semester_id
            where (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR :#{#req.idSemester} LIKE s.id)
            and (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR :#{#req.idActivity} LIKE a.id)
            and (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR c.code LIKE %:#{#req.code}%)
            and (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR  c.class_period = :#{#req.classPeriod})
            and (:#{#req.idTeacher} IS NULL OR :#{#req.idTeacher} LIKE '' OR :#{#req.idTeacher} LIKE c.teacher_id)
            ORDER BY c.last_modified_date DESC
            """, countQuery = """
            SELECT COUNT(c.id)
            FROM activity a
            JOIN class c ON c.activity_id = a.id
            JOIN semester s ON s.id = a.semester_id
            WHERE (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR :#{#req.idSemester} LIKE s.id)
            and (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR :#{#req.idActivity} LIKE a.id)
            and (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR c.code LIKE %:#{#req.code}%)
            and (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR  c.class_period = :#{#req.classPeriod})
            and (:#{#req.idTeacher} IS NULL OR :#{#req.idTeacher} LIKE '' OR :#{#req.idTeacher} LIKE c.teacher_id)
            """, nativeQuery = true)
    Page<AdClassResponse> findClassBySemesterAndActivity(@Param("req") AdFindClassRequest req, Pageable pageable);

    @Query(value = """
            SELECT c.id as id,
            c.code as code,
            c.start_time as start_time,
            c.password as password,
            c.class_period as class_period,
            c.class_size as class_size,
            c.descriptions as descriptions,
            c.teacher_id as teacherId,
            a.id as activityId,
            a.name as activityName,
            a.level as activityLevel,
            s.id as semesterId,
            s.name as semesterName
            FROM activity a
            JOIN class c ON c.activity_id = a.id
            JOIN semester s ON s.id = a.semester_id
            where c.id = :#{#id}
             """, nativeQuery = true)
    Optional<AdDetailClassRespone> adfindClassById(@Param("id") String id);

}