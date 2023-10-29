package com.labreportapp.labreport.core.student.service;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import com.labreportapp.labreport.core.student.model.request.StFindMemberFactoryRequest;
import com.labreportapp.labreport.core.student.model.response.StMemberFactoryCustom;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface StMemberFactoryService {

    PageableObject<StMemberFactoryCustom> getPage(final StFindMemberFactoryRequest request);

    List<SimpleEntityProjection> getRoles();

    List<SimpleEntityProjection> getTeams();

    Integer getNumberMemberFactory();
}
