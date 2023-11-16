package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.AdCreateMembersInTeamRequest;
import com.labreportapp.labreport.core.admin.model.request.AdCreateTeamRequest;
import com.labreportapp.labreport.core.admin.model.request.AdDeleteListMemberTeamFactoryRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindTeamRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateTeamRequest;
import com.labreportapp.labreport.core.admin.service.AdTeamService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.entity.TeamFactory;
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
@RequestMapping("/admin/team")
@CrossOrigin(origins = {"*"}, maxAge = -1)
public class AdTeamController {

    @Autowired
    private AdTeamService adTeamService;

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private CallApiConsumer callApiConsumer;

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

    @GetMapping("/member-team-factory/{id}")
    public ResponseObject detailMemberTeamFactory(@PathVariable("id") String id) {
        return new ResponseObject(adTeamService.detailMemberTeamFactory(id));
    }

    @GetMapping("/all-member-factory")
    public ResponseObject getAllMemberFactory() {
        return new ResponseObject(adTeamService.getAllMemberFactory());
    }

    @PostMapping("/add-members")
    public ResponseObject addMembers(@RequestBody AdCreateMembersInTeamRequest request) {
        return new ResponseObject(adTeamService.addMembers(request));
    }

    @DeleteMapping("/delete-member-team-factory/{id}")
    public ResponseObject deleteMemberTeamFactory(@PathVariable("id") String id) {
        return new ResponseObject(adTeamService.deleteMemberTeamFactory(id));
    }

    @DeleteMapping("/delete-list-member-team-factory")
    public ResponseObject deleteMemberTeamFactory(@RequestBody AdDeleteListMemberTeamFactoryRequest request) {
        return new ResponseObject(adTeamService.deleteListMemberTeamFactory(request));
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
