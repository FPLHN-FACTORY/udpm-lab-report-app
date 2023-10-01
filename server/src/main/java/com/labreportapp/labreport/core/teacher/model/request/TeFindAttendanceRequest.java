package com.labreportapp.labreport.core.teacher.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeFindAttendanceRequest {

    private String idStudent;

    private String idMeeting;

    private String nameMeeting;

    private String statusAttendance;

    private String notes;

}
