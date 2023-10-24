package com.labreportapp.portalprojects.core.admin.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author hieundph25894
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdMemberAndRoleProjectCustomResponse {

    private Integer stt;

    private String email;

    private String memberId;

    private Integer status;

    private List<AdMemberRoleBaseResponse> listRole;

    private String picture;

    private String userName;

    private String name;
}
