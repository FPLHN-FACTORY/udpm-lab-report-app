package com.labreportapp.labreport.core.teacher.model.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeFindScheduleMeetingClassRequest {

    private String idTeacher;

    private LocalDate dateNow;

}
