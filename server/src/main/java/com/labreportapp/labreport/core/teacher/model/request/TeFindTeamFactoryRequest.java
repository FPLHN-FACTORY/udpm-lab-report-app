package com.labreportapp.labreport.core.teacher.model.request;

import com.labreportapp.labreport.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author quynhncph26201
 */
@Getter
@Setter
public class TeFindTeamFactoryRequest extends PageableRequest {

    private String name;
}
