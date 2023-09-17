package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.teacher.model.request.TeFindListPointRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeExcelResponseMessage;
import com.labreportapp.labreport.core.teacher.model.response.TePointRespone;
import com.labreportapp.labreport.entity.Point;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TePointSevice {

    List<TePointRespone> getPointStudentById(String idClass);

    List<Point> addOrUpdatePoint(final TeFindListPointRequest request);

    void exportExcel(HttpServletResponse response, String idClass);

    TeExcelResponseMessage importExcel(MultipartFile file, String idClass);

}
