package com.labreportapp.labreport.core.teacher.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeScheduleUpdateMeetingRequest {

    private String idTeacher;

    private List<TeUpdateMeetingRequest> listMeeting;

}