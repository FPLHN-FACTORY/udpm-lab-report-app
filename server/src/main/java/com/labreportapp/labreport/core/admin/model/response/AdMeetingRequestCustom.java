package com.labreportapp.labreport.core.admin.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdMeetingRequestCustom {

    private String id;

    private String name;

    private Long meetingDate;

    private String meetingPeriodId;

    private String nameMeetingPeriod;

    private Integer startHour;

    private Integer startMinute;

    private Integer endHour;

    private Integer endMinute;

    private Integer typeMeeting;

    private String teacherId;

    private String userNameTeacher;

}
