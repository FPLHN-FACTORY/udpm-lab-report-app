package com.labreportapp.labreport.core.teacher.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894
 */
@Getter
@Setter
@ToString
public class TeUpdateHomeWorkAndNoteInMeetingRequest {

    private String idMeeting;

    private String idTeam;

    private String idHomeWork;

    private String descriptionsHomeWork;

    private String idNote;

    private String descriptionsNote;

    private String idReport;

    private String descriptionsReport;

}
