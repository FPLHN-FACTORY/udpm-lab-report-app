package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.AdFindMemberFactoryRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateMemberFactoryRequest;
import com.labreportapp.labreport.core.admin.service.AdMemberFactoryService;
import com.labreportapp.labreport.core.common.base.ImportExcelResponse;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/api/admin/member-factory")
public class AdMemberFactoryController {

    @Autowired
    private AdMemberFactoryService adMemberFactoryService;

    @GetMapping
    public ResponseObject getPage(final AdFindMemberFactoryRequest request) {
        return new ResponseObject(adMemberFactoryService.getPage(request));
    }

    @GetMapping("/roles")
    public ResponseObject getRoles() {
        return new ResponseObject(adMemberFactoryService.getRoles());
    }

    @GetMapping("/teams")
    public ResponseObject getTeams() {
        return new ResponseObject(adMemberFactoryService.getTeams());
    }

    @GetMapping("/number-member-factory")
    public ResponseObject getNumberMemberFactory() {
        return new ResponseObject(adMemberFactoryService.getNumberMemberFactory());
    }

    @PostMapping
    public ResponseObject addMemberFactory(@RequestParam("email") String email) {
        return new ResponseObject(adMemberFactoryService.addMemberFactory(email));
    }

    @GetMapping("/detail")
    public ResponseObject detailMemberFactory(@RequestParam("id") String id) {
        return new ResponseObject(adMemberFactoryService.detailMemberFactory(id));
    }

    @PutMapping
    public ResponseObject updateMemberFactory(@RequestBody AdUpdateMemberFactoryRequest request) {
        return new ResponseObject(adMemberFactoryService.updateMemberFactory(request));
    }

    @GetMapping("/export-template-excel")
    public ResponseEntity<byte[]> exportTemplateExcel(HttpServletResponse response) {
        try {
            ByteArrayOutputStream file = adMemberFactoryService.exportTemplateExcel(response);
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

    @GetMapping("/export-excel")
    public ResponseEntity<byte[]> exportExcel(HttpServletResponse response, final AdFindMemberFactoryRequest request) {
        try {
            ByteArrayOutputStream file = adMemberFactoryService.exportExcel(response, request);
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

    @PostMapping("/import-excel")
    public ResponseObject importExcel(@RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
        ImportExcelResponse importExcelResponse = adMemberFactoryService.importExcel(multipartFile);
        return new ResponseObject(importExcelResponse);
    }
}
