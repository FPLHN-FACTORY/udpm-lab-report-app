package com.labreportapp.portalprojects.core.admin.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AdMemberRoleBaseResponse {

    private String idRoleMemberProject;

    private String idRole;

    private String nameRole;
}
