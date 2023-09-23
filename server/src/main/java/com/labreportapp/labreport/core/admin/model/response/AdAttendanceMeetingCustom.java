package com.labreportapp.labreport.core.admin.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdAttendanceMeetingCustom {

    private String id;

    private String studentId;

    private String meetingId;

    private Integer status;

    private String name;

    private String email;
}
