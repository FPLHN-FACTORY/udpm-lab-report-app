package com.labreportapp.core.admin.service;

import com.labreportapp.core.admin.model.request.AdCreateClassRequest;
import com.labreportapp.core.admin.model.request.AdFindClassRequest;
import com.labreportapp.core.admin.model.response.AdActivityClassResponse;
import com.labreportapp.core.admin.model.response.AdClassResponse;
import com.labreportapp.core.admin.model.response.AdSemesterAcResponse;
import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.entity.Class;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface AdClassService {

    List<AdClassResponse> getAllClass();

    List<AdClassResponse> getAllClassBySemester(final AdFindClassRequest adFindClass);

    List<AdClassResponse> findClassByCondition(final String code, Long classPeriod, String idTeacher);

    List<AdSemesterAcResponse> getAllSemester();

    List<AdActivityClassResponse> getAllByIdSemester(final AdFindClassRequest adFindClass);

    Class createClass(@Valid final AdCreateClassRequest request);

    PageableObject<AdClassResponse> searchClass(final AdFindClassRequest teFindClass);

}
