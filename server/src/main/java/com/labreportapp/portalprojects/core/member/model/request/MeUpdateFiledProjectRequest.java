package com.labreportapp.portalprojects.core.member.model.request;

import com.labreportapp.portalprojects.core.admin.model.request.AdListMemberUpdateRoleRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author hieundph25894 - duchieu212
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MeUpdateFiledProjectRequest {

    @NotEmpty
    @NotBlank
    private String id;

    @NotEmpty
    @NotBlank
    private String code;

    @NotEmpty
    @NotBlank
    private String name;

    private String descriptions;

    private String groupProjectId;

    @NotNull
    private Long startTime;

    @NotNull
    private Long endTime;

    private List<String> listCategorysId;

}
