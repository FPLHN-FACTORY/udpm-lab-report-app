package com.labreportapp.labreport.core.teacher.controller;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassStatisticalRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeClassStatisticalResponse;
import com.labreportapp.labreport.core.teacher.service.TeClassService;
import com.labreportapp.labreport.core.teacher.service.TeStatisticalService;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/teacher/statistical")
@CrossOrigin(origins = {"*"})
public class TeStatisticalController {

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Autowired
    private TeClassService teClassService;

    @Autowired
    private TeStatisticalService teStatisticalService;

    @GetMapping("")
    public ResponseObject searchTeClassStatistical(final TeFindClassStatisticalRequest request) {
        request.setIdTeacher(labReportAppSession.getUserId());
        PageableObject<TeClassStatisticalResponse> pageList = teClassService.searchClassStatistical(request);
        return new ResponseObject(pageList);
    }

    @GetMapping("/count-class")
    public ResponseObject countClass(final TeFindClassStatisticalRequest request) {
        request.setIdTeacher(labReportAppSession.getUserId());
        return new ResponseObject(teStatisticalService.findCount(request));
    }

    @GetMapping("/export-excel")
    public ResponseEntity<byte[]> exportExcel(HttpServletResponse response, @RequestParam("idClass") String idClass) {
//        try {
//            ByteArrayOutputStream file = tePointSevice.exportExcel(response, idClass);
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            headers.setContentDispositionFormData("attachment", "sample.xlsx");
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .body(file.toByteArray());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        return null;
    }
}
