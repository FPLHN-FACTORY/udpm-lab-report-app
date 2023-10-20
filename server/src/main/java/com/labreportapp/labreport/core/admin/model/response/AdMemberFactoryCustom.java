package com.labreportapp.labreport.core.admin.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdMemberFactoryCustom {

    private String id;

    private Integer stt;

    private String memberId;

    private String name;

    private String userName;

    private String picture;

    private String email;

    private String roleMemberFactory;

    private Integer numberTeam;

    private Integer statusMemberFactory;
}
