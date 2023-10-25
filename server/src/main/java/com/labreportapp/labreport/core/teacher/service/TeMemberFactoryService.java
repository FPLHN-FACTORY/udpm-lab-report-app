package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import com.labreportapp.labreport.core.teacher.model.request.TeFindMemberFactoryRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeMemberFactoryCustom;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface TeMemberFactoryService {

    PageableObject<TeMemberFactoryCustom> getPage(final TeFindMemberFactoryRequest request);

    List<SimpleEntityProjection> getRoles();

    List<SimpleEntityProjection> getTeams();

    Integer getNumberMemberFactory();
}
