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
public class AdUpdateMeetingRequest {

    @NotBlank
    private String id;

    @NotNull
    private Long meetingDate;

    @NotBlank
    private String meetingPeriod;

    @NotNull
    private Integer typeMeeting;

    private String address;

    private String descriptions;

    private String teacherId;
}
