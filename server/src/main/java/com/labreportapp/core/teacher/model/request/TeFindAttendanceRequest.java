package com.labreportapp.core.teacher.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeFindAttendanceRequest {

    private String studentId;

    private String meetingId;

    private Integer status;

}
