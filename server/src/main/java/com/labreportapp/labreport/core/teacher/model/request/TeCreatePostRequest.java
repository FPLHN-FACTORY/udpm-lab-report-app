package com.labreportapp.labreport.core.teacher.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
@Builder
public class TeCreatePostRequest {

    @NotBlank
    private String descriptions;

    private String idClass;

}
