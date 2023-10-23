package com.labreportapp.labreport.core.admin.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdDeleteListMemberTeamFactoryRequest {

    private List<String> listIdMemberFactory;
}
