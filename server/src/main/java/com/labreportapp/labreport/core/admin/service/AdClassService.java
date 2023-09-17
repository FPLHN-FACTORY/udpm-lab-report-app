package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.AdCreateClassRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindClassRequest;
import com.labreportapp.labreport.core.admin.model.response.*;
import com.labreportapp.labreport.core.common.base.PageableObject;
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

    AdClassCustomResponse createClass(@Valid final AdCreateClassRequest request);

    AdClassCustomResponse updateClass(@Valid final AdCreateClassRequest request, String id);

    PageableObject<AdListClassCustomResponse> searchClass(final AdFindClassRequest teFindClass);

    AdDetailClassRespone adFindClassById(final String id);

}
