package com.labreportapp.labreport.core.admin.controller;


import com.labreportapp.labreport.core.admin.model.response.AdFeedBackResponse;
import com.labreportapp.labreport.core.admin.model.response.AdStudentCallApiResponse;
import com.labreportapp.labreport.core.admin.service.AdFeedBackService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author quynhncph26201
 */
@RestController
@RequestMapping("/admin/feed-back")
@CrossOrigin(origins = {"*"})
public class AdFeedBackController {
    @Autowired
    private AdFeedBackService adFeedBackSevice;

    @GetMapping("/get/{idClass}")
    public ResponseObject getFeedBackByIdClass(@PathVariable("idClass") String idClass) {
        List<AdFeedBackResponse> list = adFeedBackSevice.searchFeedBack(idClass);
        System.out.println(list);
        return new ResponseObject(list);
    }

    @GetMapping("/get-student")
    public ResponseObject getAdStudentClasses(final TeFindStudentClasses request) {
        List<AdStudentCallApiResponse> pageList = adFeedBackSevice.searchApiStudentClassesByIdClass(request.getIdClass());
        return new ResponseObject(pageList);
    }

}
