package com.labreportapp.labreport.core.admin.model.request;

import com.labreportapp.labreport.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author quynhncph26201
 */
@Getter
@Setter
public class AdFindTeamRequest extends PageableRequest {
    private String name;
}