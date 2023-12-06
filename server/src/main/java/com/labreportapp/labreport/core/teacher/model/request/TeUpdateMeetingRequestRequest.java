package com.labreportapp.labreport.core.teacher.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894 - duchieu212
 */
@Getter
@Setter
@ToString
public class TeUpdateMeetingRequestRequest {

    @NotBlank
    private String id;

    @NotNull
    private Long meetingDate;

    @NotBlank
    private String meetingPeriod;

    @NotNull
    private Integer typeMeeting;

    private String teacherId;
}
