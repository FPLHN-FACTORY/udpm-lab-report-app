package com.labreportapp.core.teacher.model.response;

import com.labreportapp.core.teacher.model.response.Base.TeAttendanceStudentMeetingRespone;
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
public class TeAttendanceStudentAllRespone {

    private String idStudent;

    private String name;

    private String email;

    private List<TeAttendanceStudentMeetingRespone> listAttendance;

}
