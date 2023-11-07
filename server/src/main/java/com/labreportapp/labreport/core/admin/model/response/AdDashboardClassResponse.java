package com.labreportapp.labreport.core.admin.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author todo thangncph26123
 */
@Getter
@Setter
public class AdDashboardClassResponse {

    private String id;

    private String code;

    private String nameLevel;

    private String nameActivity;

    private String classSize;

    private Integer numberPost;

    private Integer numberTeam;

    private Integer numberMeeting;

    private Integer numberMeetingTookPlace;

    private Integer numberStudentPass;

    private Integer numberStudentFail;
}
