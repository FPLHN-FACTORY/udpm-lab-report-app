package com.labreportapp.portalprojects.core.member.model.request;

import com.labreportapp.portalprojects.infrastructure.constant.EntityProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class MeBasePeriodRequest {

    @NotBlank
    @Length(max = EntityProperties.LENGTH_NAME)
    private String name;

    @Length(max = EntityProperties.LENGTH_DESCRIPTION)
    private String descriptions;

    @NotBlank
    private String startTime;

    @NotBlank
    private String endTime;

    @Length(max = EntityProperties.LENGTH_DESCRIPTION)
    private String target;

    @NotBlank
    private String projectId;
}
