package com.labreportapp.labreport.core.student.service;

import com.labreportapp.labreport.core.student.model.request.StClassRequest;
import com.labreportapp.labreport.core.student.model.request.StFindClassRequest;
import com.labreportapp.labreport.core.student.model.response.StClassCustomResponse;

import java.util.List;

public interface StClassService {

  List<StClassCustomResponse> getAllClassByCriteriaAndIsActive(final StFindClassRequest req);

  StClassCustomResponse joinClass(final StClassRequest req);



}
