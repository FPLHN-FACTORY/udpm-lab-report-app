package com.labreportapp.core.admin.model.request;

import com.labreportapp.infrastructure.constant.ClassPeriod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
