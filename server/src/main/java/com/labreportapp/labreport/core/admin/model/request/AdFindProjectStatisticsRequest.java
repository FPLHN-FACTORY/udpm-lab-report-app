package com.labreportapp.labreport.core.admin.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894-duchieu212
 */
@Getter
@Setter
@ToString
public class AdFindProjectStatisticsRequest {

    private String startTime;

    private String endTime;

    private Long startTimeLong;

    private Long endTimeLong;
}
