package com.labreportapp.portalprojects.core.member.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class MeCreateMemberProjectRequest {

    private String memberId;

    private String email;

    private String projectId;

    private List<String> role;
}
