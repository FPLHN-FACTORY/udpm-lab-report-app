package com.labreportapp.labreport.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author quynhncph26201
 */
@Getter
@Setter
public class AdCreateClassRequest {

    @NotBlank
    private String code;

    @NotNull
    private Long classPeriod;

    @NotNull
    private Long startTime;

    @NotBlank
    private String teacherId;

    @NotBlank
    private String activityId;

}