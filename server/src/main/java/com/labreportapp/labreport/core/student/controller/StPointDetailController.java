package com.labreportapp.labreport.core.student.controller;


import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.student.service.StPointDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student/my-class")
@CrossOrigin("*")
public class StPointDetailController {
    @Autowired
    private StPointDetailService stMyPointClassService;

    @GetMapping("/point")
    public ResponseObject getAllPointMyClass( @RequestParam("idStudent") String idStudent, @RequestParam("idClass") String idClass){
        return new ResponseObject(stMyPointClassService.getMyPointClass(idClass,idStudent));
    }

}