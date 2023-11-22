package com.labreportapp.labreport.core.student.controller;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.student.model.request.StFindPostRequest;
import com.labreportapp.labreport.core.student.model.response.StPostTeacherResponse;
import com.labreportapp.labreport.core.student.service.StPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author quynhncph26201
 */
@RestController
@RequestMapping("/api/student/post")
public class StPostController {

    @Autowired
    private StPostService stPostService;

    @GetMapping("")
    public ResponseObject searchStPost(final StFindPostRequest repquest) {
        PageableObject<StPostTeacherResponse> pageList = stPostService.searchPagePost(repquest);
        return new ResponseObject(pageList);
    }
}
