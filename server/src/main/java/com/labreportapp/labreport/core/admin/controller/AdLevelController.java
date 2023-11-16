package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.AdCreateLevelRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindLevelRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateLevelRequest;
import com.labreportapp.labreport.core.admin.model.response.AdLevelResponse;
import com.labreportapp.labreport.core.admin.service.AdLevelService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.entity.Level;
import com.labreportapp.labreport.infrastructure.logger.LoggerObject;
import com.labreportapp.labreport.util.CallApiConsumer;
import com.labreportapp.labreport.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author quynhncph26201
 */
@RestController
@RequestMapping("/admin/level")
public class AdLevelController {

    @Autowired
    private AdLevelService adLevelService;

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private CallApiConsumer callApiConsumer;

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

    @GetMapping("/download-log")
    public ResponseEntity<Resource> downloadCsv() {
        String pathFile = loggerUtil.getPathFileSendLogScreen("");
        return callApiConsumer.handleCallApiDowloadFileLog(pathFile);
    }

    @GetMapping("/history")
    public ResponseEntity<?> showHistory(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                         @RequestParam(name = "size", defaultValue = "50") Integer size
    ) {
        String pathFile = loggerUtil.getPathFileSendLogScreen("");
        LoggerObject loggerObject = new LoggerObject();
        loggerObject.setPathFile(pathFile);
        return new ResponseEntity<>(callApiConsumer.handleCallApiReadFileLog(loggerObject, page, size), HttpStatus.OK);
    }

}
