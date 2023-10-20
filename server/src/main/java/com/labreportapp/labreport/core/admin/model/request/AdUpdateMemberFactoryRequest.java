package com.labreportapp.labreport.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdUpdateMemberFactoryRequest {

    @NotBlank
    private String id;

    private List<String> teams;

    private List<String> roles;

    @NotNull
    private Integer status;
}
