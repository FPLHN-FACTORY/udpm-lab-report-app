package com.labreportapp.portalprojects.core.admin.service;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.portalprojects.core.admin.model.request.AdCreateGroupProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdFindGroupProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateGroupProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.response.AdGroupProjectResponse;
import jakarta.validation.Valid;

import java.io.IOException;

/**
 * @author thangncph26123
 */
public interface AdGroupProjectService {

    PageableObject<AdGroupProjectResponse> getAllPage(final AdFindGroupProjectRequest request);

    AdGroupProjectResponse updateGroupProject(@Valid AdUpdateGroupProjectRequest request) throws IOException;

    AdGroupProjectResponse createGroupProject(@Valid AdCreateGroupProjectRequest request) throws IOException;
}