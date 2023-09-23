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
public class TeFindMeetingRequest {

    private String idClass;

    private String idTeam;

    private String idMeeting;

}
