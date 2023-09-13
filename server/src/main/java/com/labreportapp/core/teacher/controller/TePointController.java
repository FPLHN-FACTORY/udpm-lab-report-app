package com.labreportapp.core.teacher.controller;

import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.teacher.excel.TeExcelImportPointService;
import com.labreportapp.core.teacher.model.request.TeFindListPointRequest;
import com.labreportapp.core.teacher.model.response.TeExcelResponseMessage;
import com.labreportapp.core.teacher.model.response.TePointRespone;
import com.labreportapp.core.teacher.service.TePointSevice;
import com.labreportapp.entity.Point;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public void exportExcel(HttpServletResponse response, @RequestParam("idClass") String idClass) {
        tePointSevice.exportExcel(response, idClass);
    }

    @PostMapping("/import-excel/{idClass}")
    public ResponseObject importList(@RequestParam("multipartFile") MultipartFile multipartFile, @PathVariable("idClass") String idClass) throws IOException {
        TeExcelResponseMessage teExcelResponseMessage = tePointSevice.importExcel(multipartFile, idClass);
        return new ResponseObject(teExcelResponseMessage);
    }

}


