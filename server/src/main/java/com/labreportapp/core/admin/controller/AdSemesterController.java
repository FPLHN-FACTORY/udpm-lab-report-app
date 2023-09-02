package com.labreportapp.core.admin.controller;

import com.labreportapp.core.admin.model.request.AdCreateSemesterRequest;
import com.labreportapp.core.admin.model.request.AdFindSemesterRequest;
import com.labreportapp.core.admin.model.request.AdUpdateSemesterRequest;
import com.labreportapp.core.admin.model.response.AdSemesterResponse;
import com.labreportapp.core.admin.service.AdSemesterService;
import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.entity.Semester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/semester")
@CrossOrigin(origins = {"*"}, maxAge = -1)
public class AdSemesterController {

    @Autowired
    private AdSemesterService adSemesterService;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> getAllSemester(@PathVariable int page) {
        Pageable pageResquest = PageRequest.of(page - 1, 5);
        List<Semester> semesterList = adSemesterService.findAllSermester(pageResquest);
        return ResponseEntity.ok(semesterList);
    }

    @GetMapping("")
    public ResponseObject viewSemester(@ModelAttribute final AdFindSemesterRequest request) {
        return new ResponseObject((adSemesterService.searchSemester(request)));
    }

    @GetMapping("/search")
    public ResponseObject searchSemester(final AdFindSemesterRequest request) {
        PageableObject<AdSemesterResponse> listSemester = adSemesterService.searchSemester(request);
        return new ResponseObject(listSemester);
    }

    @PostMapping("/add")
    public ResponseObject addSemester(@RequestBody AdCreateSemesterRequest obj) {
        return new ResponseObject(adSemesterService.createSermester(obj));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject deleteSemester(@PathVariable("id") String id) {
        return new ResponseObject(adSemesterService.deleteSemester(id));
    }

    @PutMapping("/update/{id}")
    public ResponseObject updateSemester(@PathVariable("id") String id,
                                         @RequestBody AdUpdateSemesterRequest obj) {
        obj.setId(id);
        return new ResponseObject(adSemesterService.updateSermester(obj));
    }

}
