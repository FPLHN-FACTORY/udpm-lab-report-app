package com.labreportapp.infrastructure.configexcel;

import com.labreportapp.infrastructure.configexcel.Base.ExcelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/teacher/excel")
@CrossOrigin(origins = {"*"})
public class ExcelController {


    @Autowired
    private ExcelService service;

    @PostMapping("/export")
    public void exportExcel(@RequestBody ExcelRequest request) throws IOException {
        service.exportExcel(request);
    }

}

