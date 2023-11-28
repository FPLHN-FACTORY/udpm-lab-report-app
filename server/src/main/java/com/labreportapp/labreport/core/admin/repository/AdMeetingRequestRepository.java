package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.request.AdFindClassRequest;
import com.labreportapp.labreport.core.admin.model.response.AdClassHaveMeetingRequestResponse;
import com.labreportapp.labreport.core.admin.model.response.AdMeetingRequestResponse;
import com.labreportapp.labreport.core.admin.model.response.AdMeetingResponse;
import com.labreportapp.labreport.repository.MeetingRequestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author todo thangncph26123
 */
public interface AdMeetingRequestRepository extends MeetingRequestRepository {

    @Query(value = """
            WITH CountMeetingRequest AS (
                SELECT a.id, COUNT(d.id) AS number_meeting_request
                FROM class a JOIN activity b ON a.activity_id = b.id
                JOIN semester c ON c.id = b.semester_id
                JOIN meeting_request d ON a.id = d.class_id
                where (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR c.id = :#{#req.idSemester})
                and (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR b.id = :#{#req.idActivity})
                AND d.status_meeting_request = 0
                GROUP BY a.id
            )
            SELECT ROW_NUMBER() OVER(ORDER BY c.last_modified_date DESC ) AS stt,
            c.id,
            c.code, c.start_time, mp.name as name_class_period
            , mp.start_hour, mp.start_minute, mp.end_hour, mp.end_minute
            , c.class_size, c.teacher_id,a.name as nameActivity, d.name AS nameLevel,
            c.status_teacher_edit, cmtr.number_meeting_request
            FROM activity a
            JOIN class c ON c.activity_id = a.id
            LEFT JOIN meeting_period mp ON c.class_period = mp.id
            JOIN level d ON d.id = a.level_id
            JOIN semester s ON s.id = a.semester_id
            JOIN CountMeetingRequest cmtr ON cmtr.id = c.id
            where (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR s.id = :#{#req.idSemester})
            and (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR a.id = :#{#req.idActivity})
            and (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR c.code LIKE %:#{#req.code}%)
            and (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR mp.id = :#{#req.classPeriod}
            OR IF(:#{#req.classPeriod} = 'none', c.class_period IS NULL, ''))
            and (:#{#req.idTeacher} IS NULL OR :#{#req.idTeacher} LIKE '' OR c.teacher_id = :#{#req.idTeacher} 
            OR IF(:#{#req.idTeacher} = 'none', c.teacher_id IS NULL, ''))
            AND (:#{#req.levelId} IS NULL OR :#{#req.levelId} LIKE '' OR d.id = :#{#req.levelId})
            AND cmtr.number_meeting_request > 0
            ORDER BY c.last_modified_date DESC
            """, countQuery = """
            WITH CountMeetingRequest AS (
                SELECT a.id, COUNT(d.id) AS number_meeting_request
                FROM class a JOIN activity b ON a.activity_id = b.id
                JOIN semester c ON c.id = b.semester_id
                JOIN meeting_request d ON a.id = d.class_id
                where (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR c.id = :#{#req.idSemester})
                and (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR b.id = :#{#req.idActivity})
                AND d.status_meeting_request = 0
                GROUP BY a.id
            )
            SELECT COUNT(c.id)
            FROM activity a
            JOIN class c ON c.activity_id = a.id
            JOIN level d ON d.id = a.level_id
            LEFT JOIN meeting_period mp ON c.class_period = mp.id
            JOIN semester s ON s.id = a.semester_id
            JOIN CountMeetingRequest cmtr ON cmtr.id = c.id
            where (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR s.id = :#{#req.idSemester})
            and (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR a.id = :#{#req.idActivity})
            and (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR c.code LIKE %:#{#req.code}%)
            and (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR mp.id = :#{#req.classPeriod}
            OR IF(:#{#req.classPeriod} = 'none', c.class_period IS NULL, ''))
            and (:#{#req.idTeacher} IS NULL OR :#{#req.idTeacher} LIKE '' OR c.teacher_id = :#{#req.idTeacher} 
            OR IF(:#{#req.idTeacher} = 'none', c.teacher_id IS NULL, ''))
            and (:#{#req.levelId} IS NULL OR :#{#req.levelId} LIKE '' OR d.id = :#{#req.levelId})
            AND cmtr.number_meeting_request > 0
            ORDER BY c.last_modified_date DESC
            """, nativeQuery = true)
    Page<AdClassHaveMeetingRequestResponse> findClassHaveMeetingRequest(@Param("req") AdFindClassRequest req, Pageable pageable);

    @Query(value = """
            WITH CountMeetingRequest AS (
                SELECT a.id, COUNT(d.id) AS number_meeting_request
                FROM class a JOIN activity b ON a.activity_id = b.id
                JOIN semester c ON c.id = b.semester_id
                JOIN meeting_request d ON a.id = d.class_id
                where (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR c.id = :#{#req.idSemester})
                and (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR b.id = :#{#req.idActivity})
                AND d.status_meeting_request = 0
                GROUP BY a.id
            )
            SELECT COUNT(c.id)
            FROM activity a
            JOIN class c ON c.activity_id = a.id
            JOIN level d ON d.id = a.level_id
            LEFT JOIN meeting_period mp ON c.class_period = mp.id
            JOIN semester s ON s.id = a.semester_id
            JOIN CountMeetingRequest cmtr ON cmtr.id = c.id
            where (:#{#req.idSemester} IS NULL OR :#{#req.idSemester} LIKE '' OR s.id = :#{#req.idSemester})
            and (:#{#req.idActivity} IS NULL OR :#{#req.idActivity} LIKE '' OR a.id = :#{#req.idActivity})
            and (:#{#req.code} IS NULL OR :#{#req.code} LIKE '' OR c.code LIKE %:#{#req.code}%)
            and (:#{#req.classPeriod} IS NULL OR :#{#req.classPeriod} LIKE '' OR mp.id = :#{#req.classPeriod}
            OR IF(:#{#req.classPeriod} = 'none', c.class_period IS NULL, ''))
            and (:#{#req.idTeacher} IS NULL OR :#{#req.idTeacher} LIKE '' OR c.teacher_id = :#{#req.idTeacher} 
            OR IF(:#{#req.idTeacher} = 'none', c.teacher_id IS NULL, ''))
            and (:#{#req.levelId} IS NULL OR :#{#req.levelId} LIKE '' OR d.id = :#{#req.levelId})
            AND cmtr.number_meeting_request > 0
            ORDER BY c.last_modified_date DESC
            """, nativeQuery = true)
    Integer countClassHaveMeetingRequest(@Param("req") AdFindClassRequest req);

    @Query(value = """
            SELECT a.id, a.name, a.meeting_date, mp.id AS meeting_period_id, mp.name AS name_meeting_period,
            mp.start_hour, mp.start_minute, mp.end_hour, mp.end_minute,
            a.type_meeting, a.teacher_id
            FROM meeting_request a
            LEFT JOIN meeting_period mp ON mp.id = a.meeting_period
            LEFT JOIN class b ON a.class_id = b.id
            WHERE b.id = :idClass 
            AND a.status_meeting_request = 0
            ORDER BY a.meeting_date DESC, mp.start_hour DESC
            """, nativeQuery = true)
    List<AdMeetingRequestResponse> getAllMeetingRequestByIdClass(@Param("idClass") String idClass);

    @Query(value = """
            SELECT a.id
            FROM meeting_request a
            WHERE a.class_id = :idClass 
            AND a.status_meeting_request = 0
            """, nativeQuery = true)
    List<String> getListIdMeetingRequestByIdClass(@Param("idClass") String idClass);
}
