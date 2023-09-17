package com.labreportapp.portalprojects.core.member.controller;

import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import com.labreportapp.portalprojects.core.member.model.request.MeBasePeriodRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeFindPeriodRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdatePeriodRequest;
import com.labreportapp.portalprojects.core.member.service.MePeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/member/period")
@CrossOrigin("*")
public class MePeriodController {

    @Autowired
    private MePeriodService mePeriodService;

    @GetMapping("/{id}")
    public ResponseObject getAllPeriodByIdProject(final MeFindPeriodRequest request, @PathVariable("id") String id) {
        return new ResponseObject(mePeriodService.getAllPeriodByIdProject(request, id));
    }

    @GetMapping("/list-period/{id}")
    public ResponseObject getAllPeriod(final MeFindPeriodRequest request, @PathVariable("id") String id) {
        return new ResponseObject(mePeriodService.getAllPeriod(request, id));
    }

    @GetMapping("/detail/{id}")
    public ResponseObject detail(@PathVariable("id") String id) {
        return new ResponseObject(mePeriodService.findById(id));
    }

    @PostMapping
    public ResponseObject add(@RequestBody MeBasePeriodRequest meBasePeriodRequest) {
        return new ResponseObject(mePeriodService.create(meBasePeriodRequest));
    }

    @PutMapping("")
    public ResponseObject update(@RequestBody MeUpdatePeriodRequest meUpdatePeriodRequest) {
        return new ResponseObject(mePeriodService.update(meUpdatePeriodRequest));
    }

    @DeleteMapping()
    public ResponseObject delete(@RequestParam("id") String id, @RequestParam("projectId") String projectId) {
        return new ResponseObject(mePeriodService.delete(id, projectId));
    }
}
