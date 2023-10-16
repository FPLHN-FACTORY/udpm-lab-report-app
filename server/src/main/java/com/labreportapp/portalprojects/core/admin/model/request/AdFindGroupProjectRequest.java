package com.labreportapp.portalprojects.core.admin.model.request;

import com.labreportapp.labreport.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdFindGroupProjectRequest extends PageableRequest {

    private String name;
}
