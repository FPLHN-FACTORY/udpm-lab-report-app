package com.labreportapp.labreport.core.student.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class StJoinTeamRequest {

    @NotBlank
    private String idClass;

    @NotBlank
    private String idTeam;
}
