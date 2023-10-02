package com.labreportapp.labreport.core.admin.controller;


import com.labreportapp.labreport.core.admin.model.response.AdFeedBackCustom;
import com.labreportapp.labreport.core.admin.service.AdFeedBackService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<AdFeedBackCustom> list = adFeedBackSevice.searchFeedBack(idClass);
        return new ResponseObject(list);
    }

}
