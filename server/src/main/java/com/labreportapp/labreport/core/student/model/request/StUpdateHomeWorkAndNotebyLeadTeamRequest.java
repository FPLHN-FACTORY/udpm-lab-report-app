package com.labreportapp.labreport.core.student.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author quynhncph26201
 */
@Getter
@Setter
public class StUpdateHomeWorkAndNotebyLeadTeamRequest {

    private String idMeeting;

    private String idTeam;

    private String idHomeWork;

    private String descriptionsHomeWork;

    private String idNote;

    private String descriptionsNote;

    private String idReport;

    private String descriptionsReport;

}
