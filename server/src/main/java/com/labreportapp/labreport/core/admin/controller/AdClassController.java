package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.AdCreateClassRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindClassRequest;
import com.labreportapp.labreport.core.admin.model.response.AdActivityClassResponse;
import com.labreportapp.labreport.core.admin.model.response.AdListClassCustomResponse;
import com.labreportapp.labreport.core.admin.model.response.AdSemesterAcResponse;
import com.labreportapp.labreport.core.admin.service.AdClassService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author quynhncph26201
 */
@RestController
@RequestMapping("/admin/class-managerment")
@CrossOrigin("*")
public class AdClassController {
    @Autowired
    private AdClassService service;

    @GetMapping("")
    public ResponseObject getAll() {
        return new ResponseObject(service.getAllClass());
    }

    @GetMapping("/get-all/id-semester")
    public ResponseObject getAllClassByIdSemester(final AdFindClassRequest adFindClass) {
        return new ResponseObject(service.getAllClassBySemester(adFindClass));
    }

    @GetMapping("/find-by-condition")
    public ResponseObject findAllByCondition(@RequestParam("codeClass") String code,
                                             @RequestParam("classPeriod") Long classPeriod,
                                             @RequestParam("idPerson") String idTeacher
    ) {
        return new ResponseObject(service.findClassByCondition(code, classPeriod, idTeacher));
    }

    @GetMapping("/semester/getAll")
    public ResponseObject getAllSemester() {
        List<AdSemesterAcResponse> listSemester = service.getAllSemester();
        return new ResponseObject(listSemester);
    }

    @GetMapping("/id-semester")
    public ResponseObject getListActivitySemester(final AdFindClassRequest teFindClass) {
        List<AdActivityClassResponse> list = service.getAllByIdSemester(teFindClass);
        return new ResponseObject(list);
    }

    @PostMapping("/add")
    public ResponseObject addCategory(@RequestBody AdCreateClassRequest request) {
        return new ResponseObject(service.createClass(request));
    }

    @PutMapping("/update/{id}")
    public ResponseObject updateCategory(@RequestBody AdCreateClassRequest request, @PathVariable String id) {
        return new ResponseObject(service.updateClass(request, id));
    }

    @GetMapping("/getAllSearch")
    public ResponseObject searchTeClass(final AdFindClassRequest adFindClass) {
        PageableObject<AdListClassCustomResponse> pageList = service.searchClass(adFindClass);
        return new ResponseObject(pageList);
    }

    @GetMapping("/information-class/{id}")
    public ResponseObject adDetailClassById(@PathVariable("id") String id) {
        return new ResponseObject(service.adFindClassById(id));
    }
}
