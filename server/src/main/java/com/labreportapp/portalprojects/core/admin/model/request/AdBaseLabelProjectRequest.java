package com.labreportapp.portalprojects.core.admin.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author NguyenVinh
 */
@Getter
@Setter
public abstract class AdBaseLabelProjectRequest {

    private String projectId;

    private String labelId;
}
