package com.labreportapp.labreport.core.student.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.student.model.request.StFindTeamFactoryRequest;
import com.labreportapp.labreport.core.student.service.StTeamFactoryService;
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
@RequestMapping("/student/team-factory")
public class StTeamFactoryController {

    @Autowired
    private StTeamFactoryService stTeamFactoryService;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> getAllTeam(@PathVariable int page) {
        Pageable pageResquest = PageRequest.of(page - 1, 5);
        List<TeamFactory> TeamList = stTeamFactoryService.findAllTeam(pageResquest);
        return ResponseEntity.ok(TeamList);
    }

    @GetMapping("")
    public ResponseObject viewTeam(@ModelAttribute final StFindTeamFactoryRequest request) {
        return new ResponseObject((stTeamFactoryService.searchTeam(request)));
    }

    @GetMapping("/search")
    public ResponseObject searchTeam(final StFindTeamFactoryRequest request) {
        return new ResponseObject(stTeamFactoryService.searchTeam(request));
    }

    @GetMapping("/detail/{id}")
    public ResponseObject detailTeam(@PathVariable("id") String id) {
        return new ResponseObject(stTeamFactoryService.detailTeam(id));
    }

    @GetMapping("/member-team-factory/{id}")
    public ResponseObject detailMemberTeamFactory(@PathVariable("id") String id) {
        return new ResponseObject(stTeamFactoryService.detailMemberTeamFactory(id));
    }

    @GetMapping("/all-member-factory")
    public ResponseObject getAllMemberFactory() {
        return new ResponseObject(stTeamFactoryService.getAllMemberFactory());
    }

}
