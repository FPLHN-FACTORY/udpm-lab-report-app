package com.labreportapp.labreport.core.admin.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdUpdateAttendanceRequest {

    private String id;

    private String name;

    private String email;

    private String studentId;

    private String meetingId;

    private Integer status;
}
