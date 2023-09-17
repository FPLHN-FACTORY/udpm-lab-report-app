package com.labreportapp.portalprojects.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class AdBaseCategoryRequest {

    @NotEmpty
    @NotBlank
    @Length(max = 15)
    private String code;

    @NotEmpty
    @NotBlank
    @Length(max = 100)
    private String name;
}
