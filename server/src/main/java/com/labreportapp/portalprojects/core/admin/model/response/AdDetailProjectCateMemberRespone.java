package com.labreportapp.portalprojects.core.admin.model.response;

import com.labreportapp.portalprojects.entity.ProjectCategory;
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
public class AdDetailProjectCateMemberRespone {

    private String name;

    private String code;

    private String descriptions;

    private Long startTime;

    private Long endTime;

    private Float progress;

    private String statusProject;

    private String backGroundImage;

    private String backGroundColor;

    private List<AdMemberAndRoleProjectCustomResponse> listMemberRole;

    private List<ProjectCategory> listCategory;
}
