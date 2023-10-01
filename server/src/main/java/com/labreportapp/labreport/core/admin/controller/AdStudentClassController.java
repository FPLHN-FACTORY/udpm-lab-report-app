package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.excel.AdImportExcelStudentClasses;
import com.labreportapp.labreport.core.admin.model.response.AdStudentCallApiRespone;
import com.labreportapp.labreport.core.admin.service.AdStudentClassService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/student-classes")
@CrossOrigin(origins = {"*"})
public class AdStudentClassController {
  @Autowired
  private AdStudentClassService adStudentClassService;


  @GetMapping("/{idClass}")
  public ResponseObject getTeStudentClasses(@PathVariable String idClass) {
    List<AdStudentCallApiRespone> pageList = adStudentClassService.findStudentClassByIdClass(idClass);
    return new ResponseObject(pageList);
  }

  @GetMapping("/export-excel/{id}")
  public ResponseEntity<byte[]> exportExcel(HttpServletResponse response, @PathVariable("id") String idClass,
                                            @RequestParam("isSample") Boolean isSample) {
    try {
      ByteArrayOutputStream file = adStudentClassService.exportStudentsInClassExcel(response, idClass, isSample);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
      headers.setContentDispositionFormData("attachment", "students.xlsx");
      return ResponseEntity.ok()
              .headers(headers)
              .body(file.toByteArray());
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/import-excel/{id}")
  public ResponseObject importExcel(@RequestParam("multipartFile") MultipartFile multipartFile, @PathVariable String id) throws IOException {
    AdImportExcelStudentClasses importResponse = adStudentClassService.importExcelStudentsInClass(multipartFile, id);
    return new ResponseObject(importResponse);
  }

}
