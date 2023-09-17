package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.AdCreatActivityRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindActivityRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateActivityRequest;
import com.labreportapp.labreport.core.admin.model.response.AdActivityResponse;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.entity.Activity;
import com.labreportapp.labreport.entity.Semester;
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
