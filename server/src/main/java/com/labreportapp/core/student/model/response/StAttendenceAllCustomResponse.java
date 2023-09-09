package com.labreportapp.core.student.model.response;

import com.labreportapp.entity.Attendance;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StAttendenceAllCustomResponse {

    private String id;

    private String classCode;

    private List<StAttendenceAllResponse> attendences;
}
