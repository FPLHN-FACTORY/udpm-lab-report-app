package com.labreportapp.core.student.service;

import com.labreportapp.core.student.model.request.StClassRequest;
import com.labreportapp.core.student.model.request.StFindClassRequest;
import com.labreportapp.core.student.model.response.StClassCustomResponse;

import java.util.List;

public interface StClassService {

  List<StClassCustomResponse> getAllClassByCriteriaAndIsActive(final StFindClassRequest req);

  StClassCustomResponse joinClass(final StClassRequest req);



}
