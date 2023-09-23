package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.*;
import com.labreportapp.labreport.core.admin.model.response.AdLevelResponse;
import com.labreportapp.labreport.core.admin.service.AdLevelService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.entity.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author quynhncph26201
 */
@RestController
@RequestMapping("/admin/level")
@CrossOrigin(origins = {"*"}, maxAge = -1)
public class AdLevelController {
    @Autowired
    private AdLevelService adLevelService;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> getAllLevel(@PathVariable int page) {
        Pageable pageResquest = PageRequest.of(page - 1, 5);
        List<Level> LevelList = adLevelService.findAllLevel(pageResquest);
        return ResponseEntity.ok(LevelList);
    }

    @GetMapping("")
    public ResponseObject viewLevel(@ModelAttribute final AdFindLevelRequest request) {
        return new ResponseObject((adLevelService.searchLevel(request)));
    }

    @GetMapping("/search")
    public ResponseObject searchLevel(final AdFindLevelRequest request) {
        PageableObject<AdLevelResponse> listLevel = adLevelService.searchLevel(request);
        return new ResponseObject(listLevel);
    }

    @PostMapping("/add")
    public ResponseObject addLevel(@RequestBody AdCreateLevelRequest obj) {
        return new ResponseObject(adLevelService.createLevel(obj));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject deleteLevel(@PathVariable("id") String id) {
        return new ResponseObject(adLevelService.deleteLevel(id));
    }

    @PutMapping("/update/{id}")
    public ResponseObject updateLevel(@PathVariable("id") String id,
                                         @RequestBody AdUpdateLevelRequest obj) {
        obj.setId(id);
        return new ResponseObject(adLevelService.updateLevel(obj));
    }

}
