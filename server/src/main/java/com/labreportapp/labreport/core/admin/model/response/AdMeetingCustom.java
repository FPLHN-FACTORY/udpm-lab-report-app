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
public class AdMeetingCustom {

    private String id;

    private String name;

    private Long meetingDate;

    private Integer meetingPeriod;

    private Integer typeMeeting;

    private String address;

    private String teacherId;

    private String userNameTeacher;

    private String descriptions;
}
