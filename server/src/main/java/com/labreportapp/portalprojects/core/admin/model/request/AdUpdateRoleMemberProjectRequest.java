package com.labreportapp.portalprojects.core.admin.model.request;

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
@Getter
@Setter
@ToString
public class AdUpdateRoleMemberProjectRequest {

    private String idRole;

    private String nameRole;
}
