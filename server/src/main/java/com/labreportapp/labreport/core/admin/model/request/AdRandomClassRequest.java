package com.labreportapp.labreport.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdRandomClassRequest {

    @NotBlank
    private String activityId;

    @NotNull
    private Integer numberRandon;

    @NotNull
    private Long startTime;

}
