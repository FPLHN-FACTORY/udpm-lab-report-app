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

    private String code;

    private String name;

    private Long classPeriod;

    private Integer classSize;

    private Long startTime;

    private String teacherId;

    private String activityId;

}
