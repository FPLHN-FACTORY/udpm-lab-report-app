package com.labreportapp.portalprojects.core.admin.service;

import com.labreportapp.portalprojects.core.admin.model.request.AdCreatLabelRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdFindLabelRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateLabelRequest;
import com.labreportapp.portalprojects.core.admin.model.response.AdLabelReponse;
import com.labreportapp.portalprojects.core.common.base.PageableObject;
import com.labreportapp.portalprojects.entity.Label;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author NguyenVinh
 */
public interface AdLabelService {

    PageableObject<AdLabelReponse> searchLabel(final AdFindLabelRequest rep);

    Label creatLabel(@Valid final AdCreatLabelRequest command);

    Label upadteLabel(@Valid final AdUpdateLabelRequest command);

    boolean deleteLabel(final String id);

    Label getOneByIdLable (final String id);

    List<String> getAllIdByStatus (final String status);

}
