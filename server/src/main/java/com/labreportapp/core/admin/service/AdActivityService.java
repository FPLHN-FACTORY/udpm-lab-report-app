package com.labreportapp.core.admin.service;

import com.labreportapp.core.admin.model.request.AdCreatActivityRequest;
import com.labreportapp.core.admin.model.request.AdFindActivityRequest;
import com.labreportapp.core.admin.model.request.AdUpdateActivityRequest;
import com.labreportapp.core.admin.model.response.AdActivityResponse;
import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.entity.Activity;
import com.labreportapp.entity.Semester;
import jakarta.validation.Valid;

import java.util.List;

public interface AdActivityService {

    PageableObject<AdActivityResponse> searchActivity(final AdFindActivityRequest rep);

    Activity creatActivity(@Valid AdCreatActivityRequest command);

    Activity updateActivity(@Valid AdUpdateActivityRequest command);

    String deleteActivity(String id);

    Activity getOneByIdActivity(String id);

    List<String> getAllIdByStatus(String status);

    List<Semester> getSemester();

}
