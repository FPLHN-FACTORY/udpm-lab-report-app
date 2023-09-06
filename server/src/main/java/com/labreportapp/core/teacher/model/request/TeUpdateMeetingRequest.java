package com.labreportapp.core.teacher.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeUpdateMeetingRequest {

    private String idMeeting;

    private String meetingAddress;

}
