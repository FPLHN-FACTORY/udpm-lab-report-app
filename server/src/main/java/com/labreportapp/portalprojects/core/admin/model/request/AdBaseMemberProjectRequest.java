package com.labreportapp.portalprojects.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author hieundph25894
 */
@Setter
@Getter
public abstract class AdBaseMemberProjectRequest {

    @NotEmpty
    @NotBlank
    private String memberId;

    private String email;

    private String projectId;

    @NotEmpty
    @NotBlank
    private List<String> listRole;

}
