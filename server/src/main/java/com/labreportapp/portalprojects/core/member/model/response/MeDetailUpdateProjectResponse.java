package com.labreportapp.portalprojects.core.member.model.response;

import com.labreportapp.portalprojects.entity.Project;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894
 */
@Getter
@Setter
@ToString
public class MeDetailUpdateProjectResponse {

    private MeDetailProjectCateResponse projectCustom;

    private Project project;
}
