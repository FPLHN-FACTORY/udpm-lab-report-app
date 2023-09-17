package com.labreportapp.portalprojects.core.admin.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author NguyenVinh
 */
@Getter
@Setter
public class AdGetOneMemberProjectRequest extends AdBaseMemberProjectRequest{

    private String idMember;

    private String idProject;
}
