package com.labreportapp.labreport.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author quynhncph26201
 */
@Getter
@Setter
public class AdBaseRoleFactoryRequest {

    @NotEmpty
    @NotBlank
    @Length(max = 500)
    private String name;

    @Length(max = 500)
    private String descriptions;

    @NotNull
    private Integer roleDefault;
}