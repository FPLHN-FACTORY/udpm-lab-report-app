package com.labreportapp.labreport.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author todo thangncph26123
 */
@Getter
@Setter
public class AdReasonsNoApproveRequest {

    @NotEmpty
    private List<String> listIdClass;

    @NotBlank
    private String reasons;
}
