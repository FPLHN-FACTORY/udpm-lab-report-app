package com.labreportapp.portalprojects.core.member.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class MeUpdateResourceRequest {

    @NotBlank
    private String id;

    @Length(max = 100)
    private String name;

    @NotBlank
    private String url;

    @NotBlank
    private String idTodo;

    @NotBlank
    private String idTodoList;
}
