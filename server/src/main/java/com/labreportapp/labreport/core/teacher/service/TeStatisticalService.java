package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.teacher.model.request.TeFindClassStatisticalRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeCountClassReponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;

/**
 * @author hieundph25894
 */
public interface TeStatisticalService {

    ByteArrayOutputStream exportExcel(HttpServletResponse response, String idClass);

    TeCountClassReponse findCount(final TeFindClassStatisticalRequest request);
}
