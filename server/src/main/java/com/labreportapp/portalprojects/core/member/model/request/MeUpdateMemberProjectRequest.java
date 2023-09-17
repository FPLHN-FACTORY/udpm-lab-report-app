package com.labreportapp.portalprojects.core.member.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class MeUpdateMemberProjectRequest {

    private String idMember;

    private String idProject;

    private Short statusWork;

    private Short role;
}
