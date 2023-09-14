package com.labreportapp.core.teacher.model.response.Base;

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
public class TeAttendanceStudentMeetingRespone {

    private String idAttendance;

    private String statusAttendance;

    private Long meetingDate;

}
