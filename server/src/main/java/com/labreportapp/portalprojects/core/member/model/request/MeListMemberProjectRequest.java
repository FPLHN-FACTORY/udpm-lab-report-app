package com.labreportapp.portalprojects.core.member.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class MeListMemberProjectRequest {

    private List<MeCreateMemberProjectRequest> listMemberProject;
}
