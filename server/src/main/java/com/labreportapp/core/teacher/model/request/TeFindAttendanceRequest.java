package com.labreportapp.core.teacher.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894
 */
@Getter
@Setter
@ToString
public class TeFindAttendanceRequest {

    private String idStudent;

    private String idMeeting;

    private String nameMeeting;

    private String statusAttendance;

}