package com.labreportapp.labreport.core.teacher.model.response;

import com.labreportapp.labreport.core.teacher.model.response.Base.TeAttendanceStudentMeetingRespone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author hieundph25894
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TeAttendanceStudentAllResponse {

    private String idStudent;

    private String name;

    private String email;

    private List<TeAttendanceStudentMeetingRespone> listAttendance;

}
