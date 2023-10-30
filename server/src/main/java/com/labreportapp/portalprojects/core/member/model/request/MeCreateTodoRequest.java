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

    @NotBlank
    private String name;

    @NotBlank
    private String todoListId;

    @NotBlank
    private String nameTodoList;

    @NotEmpty
    private String periodId;

    @NotEmpty
    private String projectId;

//    private String idUser;

}
