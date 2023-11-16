package com.labreportapp.labreport.core.admin.controller;


import com.labreportapp.labreport.core.admin.model.response.AdFeedBackCustom;
import com.labreportapp.labreport.core.admin.service.AdFeedBackService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author quynhncph26201
 */
@RestController
@RequestMapping("/admin/feed-back")
public class AdFeedBackController {
    @Autowired
    private AdFeedBackService adFeedBackSevice;

    @GetMapping("/get/{idClass}")
    public ResponseObject getFeedBackByIdClass(@PathVariable("idClass") String idClass) {
        List<AdFeedBackCustom> list = adFeedBackSevice.searchFeedBack(idClass);
        return new ResponseObject(list);
    }

    @GetMapping("/filter-class")
    public ResponseObject getAllFeedbackDetailIdClass(@RequestParam("idClass") String idClass) {
        return new ResponseObject(adFeedBackSevice.getAllFeedbackByIdClass(idClass));
    }

}
