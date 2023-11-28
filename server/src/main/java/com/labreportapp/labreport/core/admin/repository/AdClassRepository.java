package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.request.AdFindClassRequest;
import com.labreportapp.labreport.core.admin.model.response.*;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.repository.ClassRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author quynhncph26201
 */
@Repository
public interface AdClassRepository extends ClassRepository {

    @Query(value = """ 
            select a.code,a.start_time, 
            a.class_period, a.class_size, a.teacher_id, b.name as nameActivity 
            from class a join activity b on a.activity_id = b.id
            """, nativeQuery = true)
    List<AdClassResponse> getAllClass();

    @Query(value = """ 
            SELECT a.* FROM class a JOIN activity b ON a.activity_id = b.id
            JOIN semester c ON c.id = b.semester_id WHERE c.id = :idSemester
            """, nativeQuery = true)
    List<Class> getAllClassEntity(@Param("idSemester") String idSemester);

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
            SELECT s.id as id, s.name as name, s.start_time, s.end_time FROM semester s
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
            c.code, c.start_time, mp.name as name_class_period
            , mp.start_hour, mp.start_minute, mp.end_hour, mp.end_minute
            , c.class_size, c.teacher_id,a.name as nameActivity, d.name AS nameLevel,
            c.status_teacher_edit
            FROM activity a
            JOIN class c ON c.activity_id = a.id
            LEFT JOIN meeting_period mp ON c.class_period = mp.id
            JOIN level d ON d.id = a.level_id
            JOIN semester s ON s.id = a.semester_id
            where (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR s.id = :#{#req.idSemester})
            and (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR a.id = :#{#req.idActivity})
            and (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR c.code LIKE %:#{#req.code}%)
            and (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR mp.id = :#{#req.classPeriod}
            OR IF(:#{#req.classPeriod} = 'none', c.class_period IS NULL, ''))
            and (:#{#req.idTeacher} IS NULL OR :#{#req.idTeacher} LIKE '' OR c.teacher_id = :#{#req.idTeacher} 
            OR IF(:#{#req.idTeacher} = 'none', c.teacher_id IS NULL, ''))
            and (:#{#req.levelId} IS NULL OR :#{#req.levelId} LIKE '' OR d.id = :#{#req.levelId})
            and (:#{#req.classSize} IS NULL OR :#{#req.classSize} LIKE '' OR c.class_size = :#{#req.classSize})
            and (:#{#req.statusClass} IS NULL OR :#{#req.statusClass} LIKE '' 
            OR IF(:#{#req.statusClass} = 'yes', c.class_size >= :#{#req.valueClassSize}, c.class_size < :#{#req.valueClassSize}))
            and (:#{#req.statusTeacherEdit} IS NULL OR :#{#req.statusTeacherEdit} LIKE '' OR c.status_teacher_edit = :#{#req.statusTeacherEdit})
            ORDER BY c.last_modified_date DESC
            """, countQuery = """
            SELECT COUNT(c.id)
            FROM activity a
            JOIN class c ON c.activity_id = a.id
            JOIN level d ON d.id = a.level_id
            LEFT JOIN meeting_period mp ON c.class_period = mp.id
            JOIN semester s ON s.id = a.semester_id
            where (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR s.id = :#{#req.idSemester})
            and (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR a.id = :#{#req.idActivity})
            and (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR c.code LIKE %:#{#req.code}%)
            and (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR mp.id = :#{#req.classPeriod}
            OR IF(:#{#req.classPeriod} = 'none', c.class_period IS NULL, ''))
            and (:#{#req.idTeacher} IS NULL OR :#{#req.idTeacher} LIKE '' OR c.teacher_id = :#{#req.idTeacher} 
            OR IF(:#{#req.idTeacher} = 'none', c.teacher_id IS NULL, ''))
            and (:#{#req.levelId} IS NULL OR :#{#req.levelId} LIKE '' OR d.id = :#{#req.levelId})
            and (:#{#req.classSize} IS NULL OR :#{#req.classSize} LIKE '' OR c.class_size = :#{#req.classSize})
            and (:#{#req.statusClass} IS NULL OR :#{#req.statusClass} LIKE '' 
            OR IF(:#{#req.statusClass} = 'yes', c.class_size >= :#{#req.valueClassSize}, c.class_size < :#{#req.valueClassSize}))
            and (:#{#req.statusTeacherEdit} IS NULL OR :#{#req.statusTeacherEdit} LIKE '' OR c.status_teacher_edit = :#{#req.statusTeacherEdit})
            ORDER BY c.last_modified_date DESC
            """, nativeQuery = true)
    Page<AdClassResponse> findClassBySemesterAndActivity(@Param("req") AdFindClassRequest req, Pageable pageable);

    @Query(value = """
            SELECT c.id as id,
            c.code as code,
            c.start_time as start_time,
            c.password as password,
            mp.id as class_period_id,
            mp.name as class_period,
            mp.start_hour,
            mp.start_minute,
            mp.end_hour,
            mp.end_minute,
            c.class_size as class_size,
            c.descriptions as descriptions,
            c.teacher_id as teacherId,
            a.id as activityId,
            a.name as activityName,
            d.name as activityLevel,
            s.id as semesterId,
            s.name as semesterName,
            c.status_class, 
            c.status_teacher_edit,
            a.level_id
            FROM activity a
            JOIN level d ON a.level_id = d.id
            JOIN class c ON c.activity_id = a.id
            LEFT JOIN meeting_period mp ON mp.id = c.class_period
            JOIN semester s ON s.id = a.semester_id
            where c.id = :#{#id}
             """, nativeQuery = true)
    AdDetailClassRespone adfindClassById(@Param("id") String id);

    @Query(value = """
            SELECT a.id FROM class a WHERE a.activity_id = :activityId AND a.code = :code
            """, nativeQuery = true)
    String checkCodeExist(@Param("code") String code, @Param("activityId") String activityId);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY b.code) AS stt,
            a.code, a.start_time, mp.name AS name_class_period, mp.start_hour, mp.start_minute, mp.end_hour, mp.end_minute, 
            a.class_size, a.teacher_id, c.name as name_level,
            b.name as name_activity
            FROM class a JOIN activity b ON a.activity_id = b.id
            JOIN level c ON c.id = b.level_id
            JOIN semester d ON d.id = b.semester_id
            LEFT JOIN meeting_period mp ON mp.id = a.class_period
            WHERE (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR d.id = :#{#req.idSemester})
            AND (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR b.id = :#{#req.idActivity})
            AND (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR a.code LIKE %:#{#req.code}%)
            AND (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR mp.id = :#{#req.classPeriod}
            OR IF(:#{#req.classPeriod} = 'none', a.class_period IS NULL, ''))
            AND (:#{#req.idTeacher} IS NULL OR :#{#req.idTeacher} LIKE '' OR a.teacher_id = :#{#req.idTeacher} 
            OR IF(:#{#req.idTeacher} = 'none', a.teacher_id IS NULL, ''))
            AND (:#{#req.levelId} IS NULL OR :#{#req.levelId} LIKE '' OR c.id = :#{#req.levelId})
            AND (:#{#req.classSize} IS NULL OR :#{#req.classSize} LIKE '' OR a.class_size = :#{#req.classSize})
            AND (:#{#req.statusClass} IS NULL OR :#{#req.statusClass} LIKE '' 
            OR IF(:#{#req.statusClass} = 'yes', a.class_size >= :#{#req.valueClassSize}, a.class_size < :#{#req.valueClassSize}))
            AND (:#{#req.statusTeacherEdit} IS NULL OR :#{#req.statusTeacherEdit} LIKE '' OR a.status_teacher_edit = :#{#req.statusTeacherEdit})
            ORDER BY b.code
            """, nativeQuery = true)
    List<AdExportExcelClassResponse> findClassExportExcel(@Param("req") AdFindClassRequest req);

    @Query(value = """
            SELECT c.code as code
            FROM class c where c.id = :idClass
            """, nativeQuery = true)
    String findCodeByIdClass(@Param("idClass") String idClass);

    @Query(value = """
            SELECT
                c.id as id,
                c.code as code,
                c.teacher_id as idTeacher
            FROM activity a
            JOIN level l ON l.id = a.level_id
            JOIN class c ON c.activity_id = a.id
            JOIN semester s ON s.id = a.semester_id
            where (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR :#{#req.idSemester} LIKE s.id)
            and (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR :#{#req.idActivity} LIKE a.id)
            """, nativeQuery = true)
    List<AdFindSelectClassResponse> listClassFindIdActivityAndIdSemester(@Param("req") AdFindClassRequest req);


}