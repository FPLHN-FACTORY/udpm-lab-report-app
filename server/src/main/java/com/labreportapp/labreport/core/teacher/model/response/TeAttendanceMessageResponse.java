package com.labreportapp.labreport.core.teacher.model.response;

import com.labreportapp.labreport.entity.Attendance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author hieundph25894
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeAttendanceMessageResponse {

    private List<Attendance> listAttendance;

    private String message;

}
