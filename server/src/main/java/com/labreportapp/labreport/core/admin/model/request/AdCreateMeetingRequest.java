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
public class AdCreateMeetingRequest {

    @NotBlank
    private String name;

    @NotNull
    private Long meetingDate;

    @NotNull
    private Integer meetingPeriod;

    @NotNull
    private Integer typeMeeting;

    private String address;

    private String descriptions;

    @NotBlank
    private String classId;
}