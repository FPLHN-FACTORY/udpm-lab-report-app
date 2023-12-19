package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.core.teacher.model.request.TeFindClassRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassSelectRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassSentStudentRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassStatisticalRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeClassResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeClassStatisticalResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeCountClassReponse;
import com.labreportapp.labreport.core.teacher.model.response.TeDetailClassResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeFindClassSelectResponse;
import com.labreportapp.labreport.entity.Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author hieundph25894
 */
@Repository
public interface TeClassRepository extends JpaRepository<Class, String> {

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY c.code ASC ) AS stt,
            c.code AS code,
            c.id AS id,
            c.start_time AS start_time,
            c.password AS password,
            mp.name AS class_period, mp.start_hour AS start_hour, mp.start_minute AS start_minute ,
            mp.end_hour AS end_hour, mp.end_minute AS end_minute,
            c.class_size AS class_size,
            c.teacher_id AS teacher_id,
            c.activity_id AS activity_id,
            c.created_date AS created_date,
            c.descriptions AS descriptions,
            l.name AS level,
            a.name AS activity
            FROM activity a
            JOIN level l ON l.id = a.level_id
            JOIN class c ON c.activity_id = a.id
            LEFT JOIN meeting_period mp ON mp.id = c.class_period
            JOIN semester s ON s.id = a.semester_id
            WHERE c.teacher_id = :#{#req.idTeacher}
            AND (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR :#{#req.idSemester} LIKE s.id)
            AND (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR :#{#req.idActivity} LIKE a.id)
            AND (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR c.code LIKE %:#{#req.code}%)
            AND (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR  c.class_period = :#{#req.classPeriod})
            AND (:#{#req.level} IS NULL OR :#{#req.level} LIKE '' OR l.id = :#{#req.level})
                 ORDER BY c.code ASC
                     """, countQuery = """
            SELECT COUNT(c.id) FROM activity a
            JOIN level l ON l.id = a.level_id
            JOIN class c ON c.activity_id = a.id
            LEFT JOIN meeting_period mp ON c.class_period = mp.id 
            JOIN semester s ON s.id = a.semester_id
            WHERE c.teacher_id = :#{#req.idTeacher}
            AND (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR :#{#req.idSemester} LIKE s.id)
            AND (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR :#{#req.idActivity} LIKE a.id)
            AND (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR c.code LIKE %:#{#req.code}%)
            AND (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR  c.class_period = :#{#req.classPeriod})
            AND (:#{#req.level} IS NULL OR :#{#req.level} LIKE '' OR l.id = :#{#req.level})   
            """, nativeQuery = true)
    Page<TeClassResponse> findClassBySemesterAndActivity(@Param("req") TeFindClassRequest req, Pageable pageable);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY c.code ASC ) AS stt,
            c.code AS code,
            c.id AS id,
            c.start_time AS start_time,
            c.password AS password,
            mp.name AS class_period, mp.start_hour AS start_hour, mp.start_minute AS start_minute ,
            mp.end_hour AS end_hour, mp.end_minute AS end_minute,
            c.class_size AS class_size,
            c.teacher_id AS teacher_id,
            c.activity_id AS activity_id,
            c.created_date AS created_date,
            c.descriptions AS descriptions,
            l.name AS level,
            a.name AS activity
            FROM activity a
            JOIN level l ON l.id = a.level_id
            JOIN class c ON c.activity_id = a.id
            LEFT JOIN meeting_period mp ON mp.id = c.class_period
            JOIN semester s ON s.id = a.semester_id
            WHERE c.id =:idClass
                     """, nativeQuery = true)
    TeClassResponse findClassMyTeacherByIdClass(@Param("idClass") String idClass);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY c.code ASC) AS stt,
                c.code AS code,
                c.id AS id,
                c.start_time AS start_time,
                c.password AS password,
                mp.name AS class_period, mp.start_hour AS start_hour, mp.start_minute AS start_minute,
                mp.end_hour AS end_hour, mp.end_minute AS end_minute,                                        
                c.class_size AS class_size,
                c.teacher_id AS teacher_id,
                c.activity_id AS activity_id,
                c.created_date AS created_date,
                c.descriptions AS descriptions,
                l.name AS level,
                a.name AS activity,
                c.teacher_id AS teacher_id
            FROM activity a
            JOIN level l ON l.id = a.level_id
            JOIN class c ON c.activity_id = a.id
            LEFT JOIN meeting_period mp ON c.class_period = mp.id 
            JOIN semester s ON s.id = a.semester_id
            WHERE 
              DATE(CONVERT_TZ(FROM_UNIXTIME(c.start_time / 1000), 'UTC', 'Asia/Ho_Chi_Minh')) <= :#{#req.dateNow}
                AND c.class_size + :#{#req.countStudent} <= :#{#size}
                AND s.id = :#{#req.idSemester}
                AND a.id = :#{#req.idActivity}
                AND l.id = :#{#req.idLevel}
                AND c.id <> :#{#req.idClass}
            ORDER BY c.code ASC
                     """, countQuery = """
            SELECT COUNT(c.id)
              FROM activity a
            JOIN level l ON l.id = a.level_id
            JOIN class c ON c.activity_id = a.id
            LEFT JOIN meeting_period mp ON c.class_period = mp.id 
            JOIN semester s ON s.id = a.semester_id
            WHERE  DATE(CONVERT_TZ(FROM_UNIXTIME(c.start_time / 1000), 'UTC', 'Asia/Ho_Chi_Minh')) <= :#{#req.dateNow}
                AND c.class_size + :#{#req.countStudent} <= :#{#size}
                AND s.id = :#{#req.idSemester}
                AND a.id = :#{#req.idActivity}
                AND l.id = :#{#req.idLevel}
                AND c.id <> :#{#req.idClass}
            """, nativeQuery = true)
    Page<TeClassResponse> findClassBySentStudent(@Param("req") TeFindClassSentStudentRequest req, Pageable pageable, @Param("size") Integer size);
    // DATE(FROM_UNIXTIME(c.start_time / 1000)) <= :#{#req.dateNow}

    @Query(value = """
            SELECT c.id AS id_class,
            c.code AS code,
            c.start_time AS start_time,
            c.password AS password,
            mp.name AS class_period,
            c.class_size AS class_size,
            a.name AS activityName,
            c.descriptions AS descriptions,
            d.name AS activityLevel,
            s.name AS semesterName,
            c.status_class AS status_class,
            a.allow_use_trello AS allow_use_trello,
            c.status_teacher_edit AS status_teacher_edit,
            a.id AS activity_id,
            d.id AS level_id,
            s.id AS semester_id
            FROM activity a
            JOIN class c ON c.activity_id = a.id
            LEFT JOIN meeting_period mp ON c.class_period = mp.id 
            JOIN semester s ON s.id = a.semester_id
            JOIN level d ON d.id = a.level_id
            WHERE c.id = :#{#id}
             """, nativeQuery = true)
    Optional<TeDetailClassResponse> findClassById(@Param("id") String id);

    @Query(value = """
            WITH LatestSemester AS (
                 SELECT id, start_time, end_time
                 FROM semester
                 WHERE UNIX_TIMESTAMP(NOW()) BETWEEN start_time / 1000 AND end_time / 1000
                 ORDER BY end_time DESC
                 LIMIT 1
                 )
            SELECT
            ROW_NUMBER() OVER(ORDER BY l.start_time DESC) AS stt,
            c.id AS id,
            c.code AS code,
            l.start_time AS start_time,
            l.end_time AS end_time,
            mp.name AS class_period,
            a.level AS level
            FROM class c
            LEFT JOIN meeting_period mp ON mp.id = c.class_period
            JOIN activity a ON a.id = c.activity_id
            JOIN LatestSemester l ON l.id = a.semester_id
            WHERE c.teacher_id = :#{#id}
             """, nativeQuery = true)
    List<TeClassResponse> getClassClosestToTheDateToSemester(@Param("id") String id);

    @Query(value = """
            WITH check_team AS (
                SELECT c.id AS class_id, COUNT(t.class_id) AS count_team
                FROM class c
                JOIN activity a ON a.id = c.activity_id
                JOIN semester s ON s.id = a.semester_id
                LEFT JOIN team t ON t.class_id = c.id
                WHERE c.teacher_id = :#{#req.idTeacher}
                    AND (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR :#{#req.idSemester} LIKE s.id)
                    AND (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR :#{#req.idActivity} LIKE a.id)
                GROUP BY c.id
                  ORDER BY c.code ASC
            ), check_lesson AS(
                SELECT DISTINCT  c.id AS class_id,
                    COUNT(m.class_id) AS count_lesson,
                    SUM(CASE WHEN m.status_meeting = 1 THEN 1 ELSE 0 END) AS count_lesson_off
                FROM class c
                JOIN activity a ON a.id = c.activity_id
                JOIN semester s ON s.id = a.semester_id
                LEFT JOIN meeting m ON c.id = m.class_id
                WHERE c.teacher_id = :#{#req.idTeacher}
                    AND (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR :#{#req.idSemester} LIKE s.id)
                    AND (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR :#{#req.idActivity} LIKE a.id)
                GROUP BY c.id
                  ORDER BY c.code ASC
            ), check_post AS(
                 SELECT DISTINCT c.id AS class_id,
                    COUNT(p.class_id) AS count_post
                FROM class c
                JOIN activity a ON a.id = c.activity_id
                JOIN semester s ON s.id = a.semester_id
                LEFT JOIN post p ON c.id = p.class_id
                WHERE c.teacher_id = :#{#req.idTeacher}
                    AND (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR :#{#req.idSemester} LIKE s.id)
                    AND (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR :#{#req.idActivity} LIKE a.id)
                GROUP BY c.id 
                ORDER BY c.code ASC
            ),check_status_student AS (
                SELECT DISTINCT c.id AS class_id,
                   COALESCE(SUM(CASE WHEN sc.status = 0 THEN 1 ELSE 0 END), 0) AS count_pass,
                   COALESCE(SUM(CASE WHEN sc.status = 1 THEN 1 ELSE 0 END), 0) AS count_faild
                FROM class c 
                LEFT JOIN student_classes sc ON c.id = sc.class_id
                JOIN activity a ON a.id = c.activity_id
                JOIN semester s ON s.id = a.semester_id
                WHERE c.teacher_id = :#{#req.idTeacher}
                    AND (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR :#{#req.idSemester} LIKE s.id)
                    AND (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR :#{#req.idActivity} LIKE a.id)
                GROUP BY c.id
                ORDER BY c.code ASC
            )
                SELECT DISTINCT   c.id AS id,
                ROW_NUMBER() OVER(ORDER BY c.code ASC ) AS stt,      
                    c.code AS code,
                    c.start_time AS start_time,
                    mp.name AS class_period, mp.start_hour AS start_hour, mp.start_minute AS start_minute,
                    mp.end_hour AS end_hour, mp.end_minute AS end_minute,
                    c.class_size AS class_size,
                    c.teacher_id AS teacher_id,
                    c.activity_id AS activity_id,
                    c.created_date AS created_date,
                    c.descriptions AS descriptions,
                    l.name AS level,
                    a.name AS activity,
                    ct.count_team AS count_team,
                    cls.count_lesson AS count_lesson,
                    cls.count_lesson_off AS count_lesson_off,
                    cp.count_post AS count_post,
                    cst.count_pass AS count_pass,
                    cst.count_faild AS count_faild
                FROM activity a
                JOIN level l ON l.id = a.level_id
                JOIN class c ON c.activity_id = a.id
                LEFT JOIN meeting_period mp ON mp.id = c.class_period
                JOIN semester s ON s.id = a.semester_id
                JOIN check_team ct ON ct.class_id = c.id
                JOIN check_lesson cls ON cls.class_id = c.id
                JOIN check_post cp ON cp.class_id = c.id
                JOIN check_status_student cst ON cst.class_id = c.id
                WHERE c.teacher_id = :#{#req.idTeacher}
                AND (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR :#{#req.idSemester} LIKE s.id)
                AND (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR :#{#req.idActivity} LIKE a.id)
                GROUP BY c.code, c.id, c.start_time,  mp.name, mp.start_hour, mp.start_minute,mp.end_hour, mp.end_minute,
                    c.class_size, c.teacher_id, c.activity_id, c.created_date, c.descriptions, l.name, a.name, 
                    ct.count_team, cls.count_lesson, cls.count_lesson_off, cp.count_post,cst.count_pass,
                    cst.count_faild
                ORDER BY c.code ASC
            """, nativeQuery = true)
    Page<TeClassStatisticalResponse> findClassStatistical(@Param("req") TeFindClassStatisticalRequest req, Pageable pageable);

    @Query(value = """
            WITH count_teacher AS (
                SELECT COUNT(c.teacher_id) AS class_lesson  
                FROM class c
                JOIN activity a ON a.id = c.activity_id
                JOIN semester s ON s.id = a.semester_id
                WHERE c.teacher_id = :#{#req.idTeacher}
                    AND (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR :#{#req.idSemester} LIKE s.id)
                    AND (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR :#{#req.idActivity} LIKE a.id)
            ),
             count_class AS (
                  SELECT COUNT(c.id) AS class_number
                  FROM class c
                  JOIN activity a ON a.id = c.activity_id
                  JOIN semester s ON s.id = a.semester_id
                  WHERE (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR :#{#req.idSemester} LIKE s.id)
                            AND (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR :#{#req.idActivity} LIKE a.id)
                        )
             SELECT ct.class_lesson, cc.class_number
             FROM count_teacher ct JOIN count_class cc
            """, nativeQuery = true)
    TeCountClassReponse findCount(@Param("req") TeFindClassStatisticalRequest req);

    @Query(value = """
            SELECT
                c.id AS id,
                c.code AS code
            FROM activity a
            JOIN level l ON l.id = a.level_id
            JOIN class c ON c.activity_id = a.id
            JOIN semester s ON s.id = a.semester_id
            WHERE c.teacher_id = :#{#req.idTeacher}
            AND (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR :#{#req.idSemester} LIKE s.id)
            AND (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR :#{#req.idActivity} LIKE a.id)
            """, nativeQuery = true)
    List<TeFindClassSelectResponse> listClassFindIdActivityAndIdSemester(@Param("req") TeFindClassSelectRequest req);

    @Query(value = """
            SELECT c.code AS code
            FROM class c WHERE c.id = :idClass
            """, nativeQuery = true)
    String findCodeByIdClass(@Param("idClass") String idClass);

}
