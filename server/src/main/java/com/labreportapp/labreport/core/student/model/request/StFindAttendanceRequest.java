package com.labreportapp.labreport.core.student.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StFindAttendanceRequest {
    String idStudent;
    String idClass;
}
