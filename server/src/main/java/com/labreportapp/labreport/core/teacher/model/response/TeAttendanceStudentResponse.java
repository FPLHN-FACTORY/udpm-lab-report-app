package com.labreportapp.labreport.core.teacher.model.response;

import com.labreportapp.labreport.infrastructure.constant.RoleTeam;
import com.labreportapp.labreport.infrastructure.constant.StatusAttendance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeAttendanceStudentResponse {

    private String idAttendance;

    private String idMeeting;

    private String idTeam;

    private String idStudentClass;

    private StatusAttendance statusAttendance;

    private String idStudent;

    private String username;

    private String email;

    private RoleTeam role;

}
