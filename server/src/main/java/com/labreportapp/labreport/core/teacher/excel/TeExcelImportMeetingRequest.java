package com.labreportapp.labreport.core.teacher.excel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894
 */
@Getter
@Setter
@ToString
public class TeExcelImportMeetingRequest {

    private String meetingDate;

    private String nameMeetingPeriod;

    private String userNameTeacher;

    private String typeMeeting;

}
