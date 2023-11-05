package com.labreportapp.portalprojects.core.member.model.request;

import com.labreportapp.portalprojects.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public final class MeFindProjectRequest extends PageableRequest {

    private String idUser;

    private String nameProject;

    private Integer status;

    private String groupProjectId;

    private String categoryId;
}
