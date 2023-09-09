package com.labreportapp.core.student.controller;

import com.labreportapp.core.student.model.response.StAttendanceRespone;
import com.labreportapp.core.student.service.StAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student/my-class")
@CrossOrigin("*")
public class StAttendanceController {
    @Autowired
    private StAttendanceService stAttendanceService;

    @GetMapping("/attendance")
    public List<StAttendanceRespone> getAllAttendanceByID(@RequestParam(name = "id") String id) {
        return stAttendanceService.getAllAttendanceById(id);
    }
}
