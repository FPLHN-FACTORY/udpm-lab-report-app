package com.labreportapp.core.teacher.model.response.Base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeAttendanceStudentMeetingRespone {

    private String idAttendance;

    private String statusAttendance;

    private Long meetingDate;

}
