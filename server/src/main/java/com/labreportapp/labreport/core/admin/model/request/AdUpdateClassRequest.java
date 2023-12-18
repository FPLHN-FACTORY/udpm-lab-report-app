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
public class AdUpdateClassRequest {

    @NotBlank
    private String classPeriod;

    @NotNull
    private Long startTime;

    private String teacherId;

    @NotBlank
    private String activityId;

    @NotNull
    private Integer statusTeacherEdit;

    @NotNull
    private Integer statusClass;

}