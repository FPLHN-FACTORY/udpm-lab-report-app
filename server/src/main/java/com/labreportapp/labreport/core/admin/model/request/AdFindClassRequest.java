package com.labreportapp.labreport.core.admin.model.request;

import com.labreportapp.labreport.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author quynhncph26201
 */
@Getter
@Setter
public class AdFindClassRequest extends PageableRequest {

    private String idSemester;

    private String idTeacher;

    private String idActivity;

    private String code;

    private String classPeriod;

    private String levelId;

}
