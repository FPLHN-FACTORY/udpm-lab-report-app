package com.labreportapp.portalprojects.core.member.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class FindHistoryProgressBarRequest {

    private Long startTime;

    private Long endTime;

    private String projectId;
}
