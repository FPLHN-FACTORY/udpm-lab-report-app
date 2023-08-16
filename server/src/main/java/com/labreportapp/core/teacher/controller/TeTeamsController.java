package com.labreportapp.core.teacher.controller;

import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.teacher.model.request.TeCreateTeamsRequest;
import com.labreportapp.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.core.teacher.model.request.TeUpdateTeamsRequest;
import com.labreportapp.core.teacher.model.response.TeStudentClassesRespone;
import com.labreportapp.core.teacher.model.response.TeTeamsRespone;
import com.labreportapp.core.teacher.service.TeTeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/teacher/teams")
@CrossOrigin(origins = {"*"})
public class TeTeamsController {

    @Autowired
    private TeTeamsService teTeamsService;

    @GetMapping("")
    public ResponseObject getTeTeamsClass(final TeFindStudentClasses idClass) {
        List<TeTeamsRespone> pageList = teTeamsService.getAllTeams(idClass);
        return new ResponseObject(pageList);
    }

    @PostMapping("")
    public ResponseObject createTeam(@RequestBody TeCreateTeamsRequest request) {
        return new ResponseObject(teTeamsService.createTeam(request));
    }

    @PutMapping("")
    public ResponseObject updateTeam(@RequestBody TeUpdateTeamsRequest request) {
        return new ResponseObject(teTeamsService.updateTeam(request));
    }

    @DeleteMapping("/{id}")
    public ResponseObject deleteTeamById(@PathVariable() String id) {
        return new ResponseObject(teTeamsService.deleteTeamById(id));
    }

}
