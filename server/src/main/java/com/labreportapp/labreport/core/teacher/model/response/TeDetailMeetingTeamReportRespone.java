package com.labreportapp.labreport.core.teacher.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeDetailMeetingTeamReportRespone {

    private String id;

    private String name;

    private String descriptions;

    private Long meetingDate;

    private Integer typeMeeting;

    private String meetingPeriod;

    private String idClass;

    private Integer statusClass;

    private String idTeacher;

    private List<TeDetailTeamReportRespone> listTeamReport;
}
