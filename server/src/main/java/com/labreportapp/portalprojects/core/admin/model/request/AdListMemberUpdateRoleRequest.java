package com.labreportapp.portalprojects.core.admin.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author hieundph25894
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdListMemberUpdateRoleRequest {

    private String memberId;

    private String email;

    private List<AdUpdateRoleMemberProjectRequest> listRole;
}
