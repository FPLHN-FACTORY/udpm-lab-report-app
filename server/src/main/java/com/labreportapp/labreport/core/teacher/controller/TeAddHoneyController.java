package com.labreportapp.labreport.core.teacher.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.service.TeAddHoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author todo thangncph26123
 */

@RestController
@RequestMapping("/add-honey")
public class TeAddHoneyController {

    @Autowired
    private TeAddHoneyService teAddHoneyService;

    @GetMapping
    public ResponseObject getAllCategory() {
        return new ResponseObject(teAddHoneyService.getAllCategory());
    }

    @PostMapping
    public ResponseObject addHoney(@RequestParam String idClass, @RequestParam String categoryId) {
        return new ResponseObject(teAddHoneyService.addHoney(idClass, categoryId));
    }
}
