package com.labreportapp.labreport.core.teacher.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TeMeetingPeriodCustomResponse {

    private String id;

    private Long startTime;

    private Long endTine;
    
}
