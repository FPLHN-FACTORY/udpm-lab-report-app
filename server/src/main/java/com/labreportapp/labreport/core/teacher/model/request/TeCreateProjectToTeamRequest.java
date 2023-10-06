package com.labreportapp.labreport.core.teacher.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeCreateProjectToTeamRequest {

    private String idClass;

    private String idTeam;

}
