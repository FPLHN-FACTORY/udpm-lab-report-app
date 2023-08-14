package com.labreportapp.core.admin.controller;

import com.labreportapp.core.admin.model.request.AdFindClassRequest;
import com.labreportapp.core.admin.model.response.AdActivityClassResponse;
import com.labreportapp.core.admin.model.response.AdSemesterAcResponse;
import com.labreportapp.core.admin.service.AdClassService;
import com.labreportapp.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
        return new ResponseObject(service.findClassByCondition(code,classPeriod,idTeacher));
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
}
