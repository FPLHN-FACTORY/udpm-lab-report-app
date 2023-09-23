package com.labreportapp.labreport.core.student.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StClassAttendenceAllCustomResponse {

    private String id;

    private String classCode;

    private List<StAttendenceAllCustomResponse> attendences;
}
