package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.AdFindMemberFactoryRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateMemberFactoryRequest;
import com.labreportapp.labreport.core.admin.service.AdMemberFactoryService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/admin/member-factory")
@CrossOrigin(origins = {"*"}, maxAge = -1)
public class AdMemberFactoryController {

    @Autowired
    private AdMemberFactoryService adMemberFactoryService;

    @GetMapping
    public ResponseObject getPage(final AdFindMemberFactoryRequest request) {
        return new ResponseObject(adMemberFactoryService.getPage(request));
    }

    @GetMapping("/roles")
    public ResponseObject getRoles() {
        return new ResponseObject(adMemberFactoryService.getRoles());
    }

    @GetMapping("/teams")
    public ResponseObject getTeams() {
        return new ResponseObject(adMemberFactoryService.getTeams());
    }

    @GetMapping("/number-member-factory")
    public ResponseObject getNumberMemberFactory() {
        return new ResponseObject(adMemberFactoryService.getNumberMemberFactory());
    }

    @PostMapping
    public ResponseObject addMemberFactory(@RequestParam("email") String email) {
        return new ResponseObject(adMemberFactoryService.addMemberFactory(email));
    }

    @GetMapping("/detail")
    public ResponseObject detailMemberFactory(@RequestParam("id") String id) {
        return new ResponseObject(adMemberFactoryService.detailMemberFactory(id));
    }

    @PutMapping
    public ResponseObject updateMemberFactory(@RequestBody AdUpdateMemberFactoryRequest request) {
        return new ResponseObject(adMemberFactoryService.updateMemberFactory(request));
    }
}
