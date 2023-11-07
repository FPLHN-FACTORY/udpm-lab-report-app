package com.labreportapp.labreport.core.teacher.model.response;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author quynhncph26201
 */
@Getter
@Setter
public class TeTeamFactoryCustom {

    private String id;

    private Integer stt;

    private String name;

    private String descriptions;

    private Integer numberMember;

    private List<SimpleResponse> listMember;

}
