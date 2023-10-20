package com.labreportapp.labreport.core.admin.model.request;

import com.labreportapp.labreport.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdFindMemberFactoryRequest extends PageableRequest {

    private String value;

    private Integer status;

    private String roleFactoryId;
}
