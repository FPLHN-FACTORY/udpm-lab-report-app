package com.labreportapp.labreport.core.teacher.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hieundph25894 - duchieu212
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeCreateMeetingRequestRequest {

    @NotNull
    private Long meetingDate;

    @NotBlank
    private String meetingPeriod;

    @NotNull
    private Integer typeMeeting;

    private String teacherId;

    private String idClass;

}
