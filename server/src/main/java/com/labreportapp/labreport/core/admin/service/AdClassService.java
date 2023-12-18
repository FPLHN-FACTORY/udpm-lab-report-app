package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.AdCreateClassRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindClassRequest;
import com.labreportapp.labreport.core.admin.model.request.AdRandomClassRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateClassRequest;
import com.labreportapp.labreport.core.admin.model.response.AdActivityClassResponse;
import com.labreportapp.labreport.core.admin.model.response.AdClassCustomResponse;
import com.labreportapp.labreport.core.admin.model.response.AdClassResponse;
import com.labreportapp.labreport.core.admin.model.response.AdDetailClassCustomResponse;
import com.labreportapp.labreport.core.admin.model.response.AdFindSelectClassCustom;
import com.labreportapp.labreport.core.admin.model.response.AdListClassCustomResponse;
import com.labreportapp.labreport.core.admin.model.response.AdSemesterAcResponse;
import com.labreportapp.labreport.core.common.base.ImportExcelResponse;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import com.labreportapp.labreport.core.teacher.model.response.TeDetailClassResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * @author quynhncph26201
 */
public interface AdClassService {

    List<AdClassResponse> getAllClass();

    List<SimpleEntityProjection> getAllLevel();

    List<AdClassResponse> getAllClassBySemester(final AdFindClassRequest adFindClass);

    List<AdClassResponse> findClassByCondition(final String code, Long classPeriod, String idTeacher);

    List<AdSemesterAcResponse> getAllSemester();

    List<AdActivityClassResponse> getAllByIdSemester(final AdFindClassRequest adFindClass);

    AdClassCustomResponse createClass(@Valid AdCreateClassRequest request);

    AdClassCustomResponse updateClass(@Valid AdUpdateClassRequest request, String id);

    PageableObject<AdListClassCustomResponse> searchClass(final AdFindClassRequest teFindClass);

    ByteArrayOutputStream exportExcelClass(HttpServletResponse response, final AdFindClassRequest request);

    AdDetailClassCustomResponse adFindClassById(final String id);

    Boolean randomClass(@Valid AdRandomClassRequest request);

    ImportExcelResponse importExcelClass(MultipartFile multipartFile, String idSemester);

    List<AdFindSelectClassCustom> listClass(final AdFindClassRequest request);

    TeDetailClassResponse findClassById(final String id);

    Boolean sendMailToStudent();

}
