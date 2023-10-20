package com.labreportapp.labreport.core.admin.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdDetailMemberFactoryResponse {

    private String id;

    private String memberId;

    private String name;

    private String userName;

    private String picture;

    private String email;

    private List<String> roles;

    private List<String> teams;

    private Integer statusMemberFactory;
}
