package com.labreportapp.labreport.core.teacher.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.excel.TeExcelImportPointService;
import com.labreportapp.labreport.core.teacher.model.request.TeFindListPointRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeExcelResponseMessage;
import com.labreportapp.labreport.core.teacher.model.response.TePointRespone;
import com.labreportapp.labreport.core.teacher.service.TePointSevice;
import com.labreportapp.labreport.entity.Point;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/teacher/point")
@CrossOrigin(origins = {"*"})
public class TePointController {

    @Autowired
    private TePointSevice tePointSevice;

    @Autowired
    private TeExcelImportPointService tePointImportService;

    @GetMapping("/get/{idClass}")
    public ResponseObject getPointByIdClass(@PathVariable("idClass") String idClass) {
        List<TePointRespone> list = tePointSevice.getPointStudentById(idClass);
        return new ResponseObject(list);
    }

    @PostMapping("")
    public ResponseObject createOrUpdate(@RequestBody TeFindListPointRequest request) {
        List<Point> list = tePointSevice.addOrUpdatePoint(request);
        return new ResponseObject(list);
    }

    @GetMapping("/export-excel")
    public ResponseEntity<byte[]> exportExcel(HttpServletResponse response, @RequestParam("idClass") String idClass) {
        try {
            ByteArrayOutputStream file = tePointSevice.exportExcel(response, idClass);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "sample.xlsx");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(file.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/import-excel/{idClass}")
    public ResponseObject importList(@RequestParam("multipartFile") MultipartFile multipartFile, @PathVariable("idClass") String idClass) throws IOException {
        TeExcelResponseMessage teExcelResponseMessage = tePointSevice.importExcel(multipartFile, idClass);
        return new ResponseObject(teExcelResponseMessage);
    }

}


