package com.labreportapp.core.admin.service;


import com.labreportapp.core.admin.model.request.AdFindClassRequest;
import com.labreportapp.core.admin.model.response.AdActivityClassResponse;
import com.labreportapp.core.admin.model.response.AdClassResponse;
import com.labreportapp.core.admin.model.response.AdSemesterAcResponse;

import java.util.List;
import java.util.UUID;

/**
 * @author quynhncph26201
 */
public interface AdClassService {
    List<AdClassResponse> getAllClass();
    List<AdClassResponse> getAllClassBySemester(final AdFindClassRequest adFindClass);
    List<AdClassResponse> findClassByCondition(final String code , Long classPeriod, String idTeacher);
    List<AdSemesterAcResponse> getAllSemester();
    List<AdActivityClassResponse> getAllByIdSemester(final AdFindClassRequest adFindClass);

}
