package com.labreportapp.portalprojects.core.member.model.request;

import com.labreportapp.portalprojects.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class MeFindPeriodRequest extends PageableRequest{

    private String namePeriod;

    private String statusPeriod;
}
