package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.excel.AdImportExcelStudentClasses;
import com.labreportapp.labreport.core.admin.model.response.AdStudentCallApiRespone;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface AdStudentClassService {

    List<AdStudentCallApiRespone> findStudentClassByIdClass(String idClass);

    ByteArrayOutputStream exportStudentsInClassExcel(HttpServletResponse response, String idCLass, Boolean isSample);

    AdImportExcelStudentClasses importExcelStudentsInClass(MultipartFile multipartFile, String idClass);
}
