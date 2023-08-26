package com.labreportapp.core.teacher.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeFindListAttendanceRequest {

    private List<TeFindAttendanceRequest> listAttendance;

}
