package com.labreportapp.labreport.core.teacher.model.request;

import com.labreportapp.labreport.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeFindScheduleNowToTime extends PageableRequest {

    private String idTeacher;

    private String time;

    private LocalDate dateNow;

}
