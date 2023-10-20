package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.*;
import com.labreportapp.labreport.core.admin.model.response.AdTeamResponse;
import com.labreportapp.labreport.core.admin.service.AdTeamService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.entity.Team;
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
@RequestMapping("/admin/team")
@CrossOrigin(origins = {"*"}, maxAge = -1)
public class AdTeamController {

    @Autowired
    private AdTeamService adTeamService;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> getAllTeam(@PathVariable int page) {
        Pageable pageResquest = PageRequest.of(page - 1, 5);
        List<TeamFactory> TeamList = adTeamService.findAllTeam(pageResquest);
        return ResponseEntity.ok(TeamList);
    }

    @GetMapping("")
    public ResponseObject viewTeam(@ModelAttribute final AdFindTeamRequest request) {
        return new ResponseObject((adTeamService.searchTeam(request)));
    }

    @GetMapping("/search")
    public ResponseObject searchTeam(final AdFindTeamRequest request) {
        return new ResponseObject(adTeamService.searchTeam(request));
    }

    @PostMapping("/add")
    public ResponseObject addTeam(@RequestBody AdCreateTeamRequest obj) {
        return new ResponseObject(adTeamService.createTeam(obj));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject deleteTeam(@PathVariable("id") String id) {
        return new ResponseObject(adTeamService.deleteTeam(id));
    }

    @PutMapping("/update/{id}")
    public ResponseObject updateTeam(@PathVariable("id") String id,
                                            @RequestBody AdUpdateTeamRequest obj) {
        obj.setId(id);
        return new ResponseObject(adTeamService.updateTeam(obj));
    }

    @GetMapping("/detail/{id}")
    public ResponseObject detailTeam(@PathVariable("id") String id) {
        return new ResponseObject(adTeamService.detailTeam(id));
    }
}
