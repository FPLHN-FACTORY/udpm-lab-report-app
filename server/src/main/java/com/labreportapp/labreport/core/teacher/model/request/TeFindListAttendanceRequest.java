package com.labreportapp.labreport.core.teacher.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author hieundph25894
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeFindListAttendanceRequest {

    private List<TeFindAttendanceRequest> listAttendance;

    private String idMeeting;

    private String idClass;

    private String codeClass;

    private String notes;
}
