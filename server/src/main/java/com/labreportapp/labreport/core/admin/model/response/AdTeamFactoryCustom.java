package com.labreportapp.labreport.core.admin.model.response;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdTeamFactoryCustom {

    private String id;

    private Integer stt;

    private String name;

    private String descriptions;

    private Integer numberMember;

    private List<SimpleResponse> listMember;
}
