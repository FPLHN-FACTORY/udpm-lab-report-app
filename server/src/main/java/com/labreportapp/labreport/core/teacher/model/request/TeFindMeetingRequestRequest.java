package com.labreportapp.labreport.core.teacher.model.request;

import com.labreportapp.labreport.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894 - duchieu212
 */
@Getter
@Setter
@ToString
public class TeFindMeetingRequestRequest extends PageableRequest {

    private String statusMeetingRequest;

    private String idClass;

}
