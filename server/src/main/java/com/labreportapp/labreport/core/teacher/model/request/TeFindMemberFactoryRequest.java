package com.labreportapp.labreport.core.teacher.model.request;

import com.labreportapp.labreport.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author quynhncph26201
 */
@Getter
@Setter
public class TeFindMemberFactoryRequest extends PageableRequest {

    private String value;

    private Integer status;

    private String roleFactoryId;

}
