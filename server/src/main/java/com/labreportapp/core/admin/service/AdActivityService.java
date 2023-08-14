package com.labreportapp.core.admin.service;

import com.labreportapp.core.admin.model.request.AdCreatActivityRequest;
import com.labreportapp.core.admin.model.request.AdFindActivityRequest;
import com.labreportapp.core.admin.model.request.AdUpdateActivityRequest;
import com.labreportapp.core.admin.model.response.AdActivityResponse;
import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.entity.Activity;
import jakarta.validation.Valid;

import java.util.List;

public interface AdActivityService {

    PageableObject<AdActivityResponse> searchActivity(final AdFindActivityRequest rep);

    Activity createActivity(@Valid final AdCreatActivityRequest command);

    Activity updateActivity(@Valid final AdUpdateActivityRequest command);

    boolean deleteActivity(final String id);

    Activity getOneByIdActivity(final String id);

    List<String> getAllIdByStatus(final String status);
}
