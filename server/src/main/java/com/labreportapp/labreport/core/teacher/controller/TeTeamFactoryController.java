package com.labreportapp.labreport.core.teacher.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.model.request.TeFindTeamFactoryRequest;
import com.labreportapp.labreport.core.teacher.service.TeTeamFactoryService;
import com.labreportapp.labreport.entity.TeamFactory;
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
@RequestMapping("/teacher/team-factory")
@CrossOrigin(origins = {"*"}, maxAge = -1)
public class TeTeamFactoryController {

    @Autowired
    private TeTeamFactoryService teTeamFactoryService;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> getAllTeam(@PathVariable int page) {
        Pageable pageResquest = PageRequest.of(page - 1, 5);
        List<TeamFactory> TeamList = teTeamFactoryService.findAllTeam(pageResquest);
        return ResponseEntity.ok(TeamList);
    }

    @GetMapping("")
    public ResponseObject viewTeam(@ModelAttribute final TeFindTeamFactoryRequest request) {
        return new ResponseObject((teTeamFactoryService.searchTeam(request)));
    }

    @GetMapping("/search")
    public ResponseObject searchTeam(final TeFindTeamFactoryRequest request) {
        return new ResponseObject(teTeamFactoryService.searchTeam(request));
    }

    @GetMapping("/detail/{id}")
    public ResponseObject detailTeam(@PathVariable("id") String id) {
        return new ResponseObject(teTeamFactoryService.detailTeam(id));
    }

    @GetMapping("/member-team-factory/{id}")
    public ResponseObject detailMemberTeamFactory(@PathVariable("id") String id) {
        return new ResponseObject(teTeamFactoryService.detailMemberTeamFactory(id));
    }

    @GetMapping("/all-member-factory")
    public ResponseObject getAllMemberFactory() {
        return new ResponseObject(teTeamFactoryService.getAllMemberFactory());
    }
}
