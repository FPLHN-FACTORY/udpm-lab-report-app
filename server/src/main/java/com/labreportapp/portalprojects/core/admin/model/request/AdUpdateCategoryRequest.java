package com.labreportapp.portalprojects.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdUpdateCategoryRequest extends AdBaseCategoryRequest{
    @NotNull
    @NotBlank
    @NotEmpty
    private String id;
}
