package com.labreportapp.labreport.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdCreateMeetingAutoRequest {

    @NotNull
    private Long meetingDate;

    @NotBlank
    private String meetingPeriod;

    @NotNull
    private Integer typeMeeting;

    @NotBlank
    private String classId;

    private String teacherId;

    @NotNull
    private Integer numberMeeting;

    @NotNull
    private Integer numberDay;
}
