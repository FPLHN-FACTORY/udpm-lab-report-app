package com.labreportapp.labreport.core.teacher.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author hieundph25894 - duchieu212
 */
@Getter
@Setter
@ToString
public class TeMeetingRequestAgainRequest {

    private String idClass;

    private List<String> listMeetingRequestAgain;
}
