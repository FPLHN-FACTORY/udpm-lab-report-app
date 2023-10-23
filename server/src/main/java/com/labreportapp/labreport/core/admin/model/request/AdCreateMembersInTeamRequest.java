package com.labreportapp.labreport.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdCreateMembersInTeamRequest {

    @NotBlank
    private String idTeam;

    private List<String> listMemberId;
}
