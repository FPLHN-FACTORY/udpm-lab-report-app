package com.labreportapp.core.student.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author quynhncph26201
 */
@Getter
@Setter
public class FindTeamClassRequest {
    private String idClass;

    private String idStudent;

    private String idTeam;

    private Long role;

    private Long status;



}
