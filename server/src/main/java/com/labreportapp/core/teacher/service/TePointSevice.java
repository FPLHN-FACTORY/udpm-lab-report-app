package com.labreportapp.core.teacher.service;

import com.labreportapp.core.teacher.model.request.TeFindListPointRequest;
import com.labreportapp.core.teacher.model.response.TePointRespone;
import com.labreportapp.entity.Point;
import com.labreportapp.core.teacher.model.request.Base.TeListPoinExcelRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * @author hieundph25894
 */
public interface TePointSevice {

    List<TePointRespone> getPointStudentById(String idClass);

    List<Point> addOrUpdatePoint(final TeFindListPointRequest request);

    void exportExcel(HttpServletResponse response, String idClass);
}
