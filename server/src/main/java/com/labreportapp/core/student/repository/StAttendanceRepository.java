package com.labreportapp.core.student.repository;

import com.labreportapp.core.student.model.request.StFindAttendanceRequest;
import com.labreportapp.core.student.model.response.StAttendanceRespone;
import com.labreportapp.entity.Attendance;
import com.labreportapp.repository.AttendanceRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StAttendanceRepository extends AttendanceRepository {
    @Query(value = """
             SELECT  m.name as name, m.meeting_date as meetingDate, m.meeting_period as meetingPeriod, m.type_meeting as typeMeeting, a.status as status
                        from meeting m\s
                        left join attendance a on a.meeting_id = m.id
                        where a.student_id = :#{#req.idStudent} and m.class_id=:#{#req.idClass}
                        group by m.name , m.meeting_date , m.meeting_period, m.type_meeting, a.status
                        order by m.meeting_date asc
            """, nativeQuery = true)
    List<StAttendanceRespone> getAllAttendanceById(@Param("req") StFindAttendanceRequest req);
}
