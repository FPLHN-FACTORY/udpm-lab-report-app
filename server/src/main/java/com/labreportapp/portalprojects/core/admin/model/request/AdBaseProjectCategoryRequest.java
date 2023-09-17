package com.labreportapp.portalprojects.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Setter
@Getter
public abstract class AdBaseProjectCategoryRequest {

    @NotEmpty
    @NotBlank
    private String projectId;

    @NotEmpty
    @NotBlank
    private String categoryId;

}
