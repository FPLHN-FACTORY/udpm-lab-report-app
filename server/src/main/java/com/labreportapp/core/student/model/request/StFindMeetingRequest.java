package com.labreportapp.core.student.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author quynhncph26201
 */
@Getter
@Setter
public class StFindMeetingRequest {
    private String idClass;

    private String idTeam;

    private String idMeeting;
}
