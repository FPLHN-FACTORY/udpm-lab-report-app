package com.labreportapp.portalprojects.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author NguyenVinh
 */
@Getter
@Setter
public abstract class AdBaseProjectRepuest {

    @NotEmpty
    @NotBlank
    private String code;

    @NotEmpty
    @NotBlank
    private String name;

    private String descriptions;

    @NotNull
    private Long startTime;

    @NotNull
    private Long endTime;

    private List<String> listCategorysId;

    private List<AdCreateMemberProjectRequest> listMembers;

}
