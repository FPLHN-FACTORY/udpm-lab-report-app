package com.labreportapp.labreport.core.student.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author quynhncph26201
 */
@Getter
@Setter
public class StFindMeetingRequest {

    private String idClass;

    private String idTeam;

    private String idMeeting;

    private String idStudent;

    private Long currentTime = new Date().getTime();

}
