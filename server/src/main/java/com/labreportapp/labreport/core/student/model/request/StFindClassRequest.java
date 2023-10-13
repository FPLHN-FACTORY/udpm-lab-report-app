package com.labreportapp.labreport.core.student.model.request;

import com.labreportapp.portalprojects.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@ToString
public class StFindClassRequest extends PageableRequest {

    private String semesterId;

    private String activityId;

    private String code;

    private String classPeriod;

    private String level;

    private String studentId;
}
