package com.labreportapp.labreport.core.student.controller;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.student.model.request.StClassRequest;
import com.labreportapp.labreport.core.student.model.request.StFindClassRequest;
import com.labreportapp.labreport.core.student.model.response.StClassCustomResponse;
import com.labreportapp.labreport.core.student.service.StClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student/class")
public class StClassController {

    @Autowired
    private StClassService stClassService;

    @GetMapping("")
    public ResponseObject getClassByCriteriaAndIsActive(final StFindClassRequest req) {
        PageableObject<StClassCustomResponse> response = stClassService.getAllClassByCriteriaAndIsActive(req);
        return new ResponseObject(response);
    }

    @PostMapping("/join")
    public ResponseObject studentJoinClass(@RequestBody StClassRequest req) {
        StClassCustomResponse response = stClassService.joinClass(req);
        return new ResponseObject(response);
    }

    @GetMapping("/{id}")
    public ResponseObject detailClass(@PathVariable("id") String id) {
        return new ResponseObject(stClassService.findClassById(id));
    }
}
