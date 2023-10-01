package com.labreportapp.labreport.core.teacher.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.model.response.TeLevelResponse;
import com.labreportapp.labreport.core.teacher.service.TeLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/teacher/level")
@CrossOrigin(origins = {"*"})
public class TeLevelController {

    @Autowired
    private TeLevelService teLevelService;

    @GetMapping("")
    public ResponseObject getAllLevel() {
        List<TeLevelResponse> list = teLevelService.getAllLevel();
        return new ResponseObject(list);
    }

}
