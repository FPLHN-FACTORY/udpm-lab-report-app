package com.labreportapp.labreport.core.admin.model.response;

import com.labreportapp.labreport.infrastructure.constant.MeetingPeriod;
import com.labreportapp.labreport.infrastructure.constant.StatusMeeting;
import com.labreportapp.labreport.infrastructure.constant.TypeMeeting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdDetailMeetingResponse {

    private String id;

    private String name;

    private Long meetingDate;

    private MeetingPeriod meetingPeriod;

    private String descriptions;

    private String classId;

    private String teacherId;

    private String userNameTeacher;

    private TypeMeeting typeMeeting;

    private String address;

    private String codeClass;

    private StatusMeeting statusMeeting;
}