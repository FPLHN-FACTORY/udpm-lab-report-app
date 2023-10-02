package com.labreportapp.portalprojects.core.member.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class MeCreateTodoRequest {

    @NotEmpty
    @NotBlank
    private String name;

    @NotEmpty
    @NotBlank
    private String todoListId;

    @NotEmpty
    @NotBlank
    private String nameTodoList;

    @NotBlank
    @NotEmpty
    private String periodId;

    @NotBlank
    @NotEmpty
    private String projectId;

//    private String idUser;

}
