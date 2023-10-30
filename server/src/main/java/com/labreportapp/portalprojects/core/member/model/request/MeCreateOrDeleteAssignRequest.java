package com.labreportapp.portalprojects.core.member.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class MeCreateOrDeleteAssignRequest extends MeTodoAndTodoListRequest{

    @NotBlank
    private String idMember;

    private String nameMember;

    private String email;

    @NotBlank
    private String idTodoCreateOrDelete;

    @NotBlank
    private String projectId;

//    private String idUser;
}
