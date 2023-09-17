package com.labreportapp.portalprojects.core.member.service;

import com.labreportapp.portalprojects.core.common.base.PageableObject;
import com.labreportapp.portalprojects.core.member.model.request.MeFindActivityRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeActivityResponse;

/**
 * @author thangncph26123
 */
public interface MeActivityService {

    PageableObject<MeActivityResponse> getAll(final MeFindActivityRequest request);
}
