package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.response.AdStudentCallApiRespone;
import com.labreportapp.labreport.core.admin.service.AdStudentClassService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
