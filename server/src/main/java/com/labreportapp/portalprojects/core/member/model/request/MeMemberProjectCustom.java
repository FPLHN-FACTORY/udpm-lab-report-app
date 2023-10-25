package com.labreportapp.portalprojects.core.member.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class MeMemberProjectCustom {

    private String id;

    private String name;

    private String userName;

    private String email;

    private String picture;

    private String memberId;

    private List<String> roles;

    private String statusWork;
}
