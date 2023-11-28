package com.labreportapp.labreport.core.teacher.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894 - duchieu212
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TeMeetingRequestCustomResponse {

    private String id;

    private String name;

    private Long meetingDate;

    private Integer typeMeeting;

    private String meetingPeriod;

    private Integer startHour;

    private Integer startMinute;

    private Integer endHour;

    private Integer endMinute;

    private String teacher;

    private Integer statusMeetingRequest;
}
