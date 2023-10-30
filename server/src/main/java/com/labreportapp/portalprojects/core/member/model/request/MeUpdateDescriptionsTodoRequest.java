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
public class MeUpdateDescriptionsTodoRequest extends MeTodoAndTodoListRequest{

    @NotBlank
    private String idTodoUpdate;

    @NotBlank
    @Length(max = 5000, message = "Mô tả tối đa 5000 ký tự")
    private String descriptions;

}
