package com.labreportapp.core.admin.model.request;

import com.labreportapp.core.common.base.PageableRequest;
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

}
